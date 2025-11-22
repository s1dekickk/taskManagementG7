package com.taskmanagement.controller;

import com.taskmanagement.dtos.CreateTaskRequest;
import com.taskmanagement.dtos.TaskDTO;
import com.taskmanagement.dtos.UpdateTaskRequest;
import com.taskmanagement.mapper.TaskMapper;
import com.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    // Create task
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            UriComponentsBuilder uriBuilder) {
        var task = taskService.createTask(request);
        var taskDTO = taskMapper.toDto(task);

        var uri = uriBuilder.path("/tasks/{id}").buildAndExpand(taskDTO.getTaskId()).toUri();
        return ResponseEntity.created(uri).body(taskDTO);
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTaskRequest request) {
        var updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    // Get all tasks
    @GetMapping
    public List<TaskDTO> getAllTasks(
            @RequestParam (required = false, defaultValue = "dueDate") String sort
    ) {
        // Basic sort field validation
        Sort sortBy = Sort.by(sort);
        return taskMapper.toDtoList(taskService.listTasks(sortBy));
    }

    // Get task by id
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Integer id) {
        var task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    // Get deleted tasks
    @GetMapping("/trash")
    public List<TaskDTO> listDeletedTasks() {
        // Assuming taskService has a dedicated method for this
        return taskMapper.toDtoList(taskService.listDeletedTasks());
    }

    // DELETE /tasks/{id} (Soft Delete)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> softDeleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // POST /tasks/{id}/restore
    @PostMapping("/{id}/restore")
    public ResponseEntity<TaskDTO> restoreTask(@PathVariable Integer id) {
        var restoredTask = taskService.restoreTask(id);
        return ResponseEntity.ok(taskMapper.toDto(restoredTask));
    }

    // DELETE /tasks/{id}/permanent (Hard Delete)
    @DeleteMapping("/{id}/permanent")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePermanently(@PathVariable Integer id) {
        taskService.deletePermanently(id);
        return ResponseEntity.noContent().build();
    }
}