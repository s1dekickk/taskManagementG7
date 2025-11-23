package com.taskmanagement.controller;
import com.taskmanagement.dtos.task.AttachmentDTO;
import com.taskmanagement.entity.task.TaskAttachment;
import com.taskmanagement.service.TaskAttachmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/attachments") // nested resource path
@AllArgsConstructor
public class TaskAttachmentController {
    private final TaskAttachmentService attachmentService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentDTO> uploadAttachment(
            @PathVariable Integer taskId,
            @RequestParam("file") MultipartFile file) { // 'file' must match frontend's input name
        // this is temporary, hardcoded user ID until security is implemented
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
    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Integer attachmentId) {
        try {
            TaskAttachment attachment = attachmentService.getAttachmentMetadata(attachmentId);
            Path filePath = Paths.get(attachment.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                String contentType = attachment.getMimeType();
                String headerValue = "attachment; filename=\"" + attachment.getFileName() + "\"";
                // set headers for file download
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(resource);
            } else {
                // If metadata exists but physical file is gone
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Attachment metadata not found
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build(); // File path is invalid
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{attachmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAttachment(@PathVariable Integer attachmentId) {
        try {
            attachmentService.deleteAttachment(attachmentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public List<AttachmentDTO> listAttachmentsByTask(@RequestParam Integer taskId) {
        return List.of();
    }
}
