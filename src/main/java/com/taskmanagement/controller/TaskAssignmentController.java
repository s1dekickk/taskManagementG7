package com.taskmanagement.controller;

import com.taskmanagement.dtos.assignments.AssignUsersRequest;
import com.taskmanagement.dtos.assignments.AssignmentDTO;
import com.taskmanagement.service.TaskAssignmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {
    private final TaskAssignmentService assignmentService;
    // GET /tasks/{taskId}/assignments
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAssignments(@PathVariable Integer taskId) {
        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignedUsers(taskId);
            return ResponseEntity.ok(assignments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // PUT /tasks/{taskId}/assignments
    // replaces the list of assigned users with the list provided in the request body.
    @PutMapping
    public ResponseEntity<List<AssignmentDTO>> setAssignments(
            @PathVariable Integer taskId,
            @Valid @RequestBody AssignUsersRequest request) {
        try {
            List<AssignmentDTO> assignments = assignmentService.setAssignedUsers(taskId, request);
            return ResponseEntity.ok(assignments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}