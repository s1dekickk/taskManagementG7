package com.taskmanagement.controller;
import com.taskmanagement.dtos.CreateTaskRequest;
import com.taskmanagement.dtos.TaskDTO;
import com.taskmanagement.dtos.UpdateTaskRequest;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.User;
import com.taskmanagement.mapper.TaskMapper;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @RequestBody CreateTaskRequest request,
            UriComponentsBuilder uriBuilder) {
        Task task = taskMapper.toEntity(request);
        task.setStatus(TaskStatus.TO_DO);
        Integer creatorId = request.getCreatorId();
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("Creator User not found for ID: " + creatorId));
        task.setDeleted(false);
        Task savedTask = taskRepository.save(task);
        TaskDTO taskDTO = taskMapper.toDto(savedTask);
        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(taskDTO.getTaskId()).toUri();
        return ResponseEntity.created(uri).body(taskDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Integer id,
            @RequestBody UpdateTaskRequest request) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null || existingTask.isDeleted()) {
            return ResponseEntity.notFound().build();
        }
        taskMapper.update(request, existingTask);
        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
    @GetMapping("/trash")
    public List<TaskDTO> listDeletedTasks() {
        return taskRepository.findByIsDeletedTrue()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTask(@PathVariable Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setDeleted(true);//set boolean -> true, soft deleted
        taskRepository.save(task);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/restore")
    public ResponseEntity<TaskDTO> restoreTask(@PathVariable Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));
        task.setDeleted(false);//set boolean false, back to original
        Task restoredTask = taskRepository.save(task);
        return ResponseEntity.ok(taskMapper.toDto(restoredTask));
    }
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> deletePermanently(@PathVariable Integer id) {
        // Check if the task exists and is marked as deleted before final deletion.
        // This is to ensure users don't accidentally hard delete active tasks.
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            // Task doesn't exist, return 404
            return ResponseEntity.notFound().build();
        }
        if (!task.isDeleted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
            //This is to ensure it doesn't delete tasks that are not currently soft deleted
        }
        taskRepository.deleteById(id);
        // Return 204 to confirm successful deletion
        return ResponseEntity.noContent().build();
    }
}
