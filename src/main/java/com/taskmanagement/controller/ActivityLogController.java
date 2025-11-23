package com.taskmanagement.controller;

import com.taskmanagement.dtos.ActivityLogDTO;
import com.taskmanagement.service.ActivityLogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/activity")
@RequiredArgsConstructor
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    // GET /tasks/{taskId}/activity?limit=10
    @GetMapping
    public ResponseEntity<List<ActivityLogDTO>> getTaskActivity(
            @PathVariable Integer taskId,
            @RequestParam(defaultValue = "10") int limit) {
        // limit
        if (limit <= 0 || limit > 100) {
            return ResponseEntity.badRequest().build();
        }
        try {
            List<ActivityLogDTO> logs = activityLogService.getTaskActivity(taskId, limit);
            return ResponseEntity.ok(logs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}