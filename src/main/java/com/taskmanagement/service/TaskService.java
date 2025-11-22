package com.taskmanagement.service;

import com.taskmanagement.dtos.CreateTaskRequest;
import com.taskmanagement.dtos.UpdateTaskRequest;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.User;
import com.taskmanagement.entity.Category;
import com.taskmanagement.entity.Tag;
import com.taskmanagement.entity.TaskAssignment;
import com.taskmanagement.entity.ActivityLog;
import com.taskmanagement.entity.ActionType;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.repository.CategoryRepository;
import com.taskmanagement.repository.TagRepository;
import com.taskmanagement.repository.TaskAssignmentRepository;
import com.taskmanagement.repository.ActivityLogRepository;
import com.taskmanagement.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final TaskAssignmentRepository assignmentRepository;
    private final ActivityLogRepository activityLogRepository;
    private final TaskMapper taskMapper;

    // typically we get the current authenticated user's ID
    // from a SecurityContextHolder, but we simulate it here with a placeholder.
    private static final Integer CURRENT_USER_ID_PLACEHOLDER = 1;

    private Set<User> resolveUsers(Set<Integer> userIds, String fieldName) {
        if (userIds == null || userIds.isEmpty()) return Collections.emptySet();

        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            Set<Integer> foundIds = users.stream().map(User::getUserId).collect(Collectors.toSet());
            userIds.removeAll(foundIds);
            throw new EntityNotFoundException(fieldName + " contains invalid User IDs: " + userIds);
        }
        return Set.copyOf(users);
    }

    // CRUD
    @Transactional
    public Task createTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);
        task.setStatus(TaskStatus.PENDING);
        task.setDeleted(false);
        // get current user from Spring Security Context
        User creator = userRepository.findById(CURRENT_USER_ID_PLACEHOLDER)
                .orElseThrow(() -> new EntityNotFoundException("Creator user not found."));
        task.setCreatedBy(creator);
        // resolve and set Category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + request.getCategoryId()));
        task.setCategory(category);

        // resolve and set Tags
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = Set.copyOf(tagRepository.findAllById(request.getTagIds()));
            if (tags.size() != request.getTagIds().size()) {
                throw new EntityNotFoundException("One or more Tag IDs provided are invalid.");
            }
            task.setTags(tags);
        }

        // save the task first to get its ID
        Task savedTask = taskRepository.save(task);

        // create TaskAssignments
        if (request.getAssignedUserIds() != null && !request.getAssignedUserIds().isEmpty()) {
            Set<User> assignedUsers = resolveUsers(request.getAssignedUserIds(), "Assigned Users");
            Set<TaskAssignment> assignments = assignedUsers.stream()
                    .map(user -> new TaskAssignment(null, savedTask, user, LocalDateTime.now()))
                    .collect(Collectors.toSet());

            assignmentRepository.saveAll(assignments);
            savedTask.setTaskAssignments(assignments);
        }

        // log the creation
        logTaskActivity(savedTask, creator, ActionType.CREATED, null, "Task created.");

        return savedTask;
    }

    public List<Task> listDeletedTasks() {
        return taskRepository.findByDeletedTrue();
    }

    public Task getTaskById(Integer taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));
    }

    public List<Task> listTasks(Sort sort) {
        return taskRepository.findAll(sort);
    }

    @Transactional
    public Task updateTask(Integer taskId, UpdateTaskRequest request) {
        Task task = getTaskById(taskId);
        User currentUser = userRepository.findById(CURRENT_USER_ID_PLACEHOLDER)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found."));
        // map simple fields first (Title, Description, Dates, Priority)
        taskMapper.update(request, task);

        // status change (Requires logging logic)
        if (request.getStatus() != null && request.getStatus() != task.getStatus()) {
            TaskStatus oldStatus = task.getStatus();
            task.setStatus(request.getStatus());
            logTaskActivity(task, currentUser, ActionType.STATUS_CHANGED, oldStatus.name(), request.getStatus().name());
        }

        // Category update
        if (request.getCategoryId() != null && !request.getCategoryId().equals(task.getCategory().getCategoryId())) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + request.getCategoryId()));
            task.setCategory(newCategory);
            logTaskActivity(task, currentUser, ActionType.UPDATED, "Category changed", newCategory.getName());
        }

        // Tag update (replace the entire set)
        if (request.getTagIds() != null) {
            Set<Tag> newTags = Set.copyOf(tagRepository.findAllById(request.getTagIds()));
            task.setTags(newTags);
            logTaskActivity(task, currentUser, ActionType.UPDATED, "Tags changed", "Tags updated.");
        }

        // Assignments update (replace the entire set)
        if (request.getAssignedUserIds() != null) {
            // Delete existing assignments first
            assignmentRepository.deleteAllByTask(task);
            // Create new assignments
            Set<User> assignedUsers = resolveUsers(request.getAssignedUserIds(), "Assigned Users");
            Set<TaskAssignment> newAssignments = assignedUsers.stream()
                    .map(user -> new TaskAssignment(null, task, user, LocalDateTime.now()))
                    .collect(Collectors.toSet());

            assignmentRepository.saveAll(newAssignments);
            task.setTaskAssignments(newAssignments);
            logTaskActivity(task, currentUser, ActionType.ASSIGNED, "Assignments changed", "Assignments updated.");
        }

        // save and return
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Integer taskId) {
        Task task = getTaskById(taskId);
        if (!task.isDeleted()) {
            task.setDeleted(true);
            taskRepository.save(task);
            User currentUser = userRepository.findById(CURRENT_USER_ID_PLACEHOLDER)
                    .orElseThrow(() -> new EntityNotFoundException("Current user not found."));
            logTaskActivity(task, currentUser, ActionType.DELETED, null, "Task soft deleted.");
        }
    }
    @Transactional
    public Task restoreTask(Integer taskId) {
        Task task = getTaskById(taskId);
        User currentUser = userRepository.findById(CURRENT_USER_ID_PLACEHOLDER)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found."));

        if (task.isDeleted()) {
            task.setDeleted(false);
            Task restoredTask = taskRepository.save(task);
            logTaskActivity(restoredTask, currentUser, ActionType.RESTORED, "Soft Deleted", "Active");
            return restoredTask;
        }
        return task;
    }

    @Transactional
    public void deletePermanently(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            return;
        }
        if (!task.isDeleted()) {
            throw new IllegalArgumentException("Task must be soft-deleted before permanent deletion.");
        }

        User currentUser = userRepository.findById(CURRENT_USER_ID_PLACEHOLDER)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found."));

        // Log activity before deletion, as the task entity will be gone
        logTaskActivity(task, currentUser, ActionType.FILE_REMOVED, null, "Permanently deleted.");

        taskRepository.delete(task);
    }

    private void logTaskActivity(Task task, User user, ActionType actionType, String oldValue, String newValue) {
        ActivityLog log = new ActivityLog();
        log.setTask(task);
        log.setUser(user);
        log.setActionType(actionType);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setDescription(actionType.name() + " on task " + task.getTitle());
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);
    }
}
