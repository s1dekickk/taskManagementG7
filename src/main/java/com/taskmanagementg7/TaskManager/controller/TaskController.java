package com.taskmanagementg7.TaskManager.controller;

import com.taskmanagementg7.TaskManager.dtos.TaskDTO;
import com.taskmanagementg7.TaskManager.mapper.TaskMapper;
import com.taskmanagementg7.TaskManager.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public TaskController(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
