package com.taskmanagement.service;

import com.taskmanagement.dtos.assignments.AssignUsersRequest;
import com.taskmanagement.dtos.assignments.AssignmentDTO;
import com.taskmanagement.entity.task.Task;
import com.taskmanagement.entity.user.User;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignedUsers(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // Assuming Task entity has a Set<User> assignedUsers field
        return task.getAssignedUsers().stream()
                .map(user -> AssignmentDTO.builder()
                        .userId(user.getUserId())
                        .fullName(user.getFullName())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Replaces the entire set of assigned users for a task.
     * This is typically easier for the frontend than individual add/remove operations.
     */
    @Transactional
    public List<AssignmentDTO> setAssignedUsers(Integer taskId, AssignUsersRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // fetch all User entities based on the requested IDs
        List<User> newUsers = userRepository.findAllById(request.getUserIds());

        // validate all requested users exist
        if (newUsers.size() != request.getUserIds().size()) {
            // Find which IDs were not found
            Set<Integer> foundIds = newUsers.stream().map(User::getUserId).collect(Collectors.toSet());
            String missingIds = request.getUserIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new EntityNotFoundException("One or more users not found: " + missingIds);
        }

        task.setAssignedUsers(new HashSet<>(newUsers));

        taskRepository.save(task);

        // return the new list of assigned users
        return newUsers.stream()
                .map(user -> AssignmentDTO.builder()
                        .userId(user.getUserId())
                        .fullName(user.getFullName())
                        .build())
                .collect(Collectors.toList());
    }
}
