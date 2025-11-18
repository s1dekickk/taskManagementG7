package com.taskmanagement.controller;
import com.taskmanagement.dtos.AttachmentDTO;
import com.taskmanagement.service.TaskAttachmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@RestController
@RequestMapping("/tasks/{taskId}/attachments") // Nested resource path
@AllArgsConstructor
public class TaskAttachmentController {
    private final TaskAttachmentService attachmentService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentDTO> uploadAttachment(
            @PathVariable Integer taskId,
            @RequestParam("file") MultipartFile file) { // 'file' must match frontend's input name
        // This is temporary, hardcoded user ID until security is implemented
        Integer tempUserId = 1;
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            // service handles saving the file to disk and creating the DB metadata
            AttachmentDTO dto = attachmentService.uploadAttachment(taskId, tempUserId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            // Handle file saving errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // add the download endpoint (GET /tasks/{taskId}/attachments/{attachmentId}/download) here
}
