package com.taskmanagement.controller;
import com.taskmanagement.dtos.CommentDTO;
import com.taskmanagement.dtos.CreateCommentRequest;
import com.taskmanagement.service.TaskCommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class TaskCommentController {
    private final TaskCommentService commentService;
    // Gets all comments for a task (including replies)
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsForTask(@PathVariable Integer taskId) {
        try {
            List<CommentDTO> comments = commentService.getCommentsByTaskId(taskId);
            return ResponseEntity.ok(comments);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Creates a new top level comment or a reply
    @PostMapping
    public ResponseEntity<CommentDTO> postComment(
            @PathVariable Integer taskId,
            @RequestBody CreateCommentRequest request) {

        // Temporary, hardcoded user ID (will be replaced by security context)
        Integer tempUserId = 1;

        try {
            CommentDTO newComment = commentService.addComment(
                    taskId,
                    tempUserId,
                    request.getText(),
                    request.getParentCommentId() // Will be null for top-level comments
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
