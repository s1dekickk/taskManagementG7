package com.taskmanagement.service;

import com.taskmanagement.dtos.AttachmentDTO;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.User;
import com.taskmanagement.entity.TaskAttachment;
import com.taskmanagement.mapper.AttachmentMapper;
import com.taskmanagement.repository.TaskAttachmentRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskAttachmentService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskAttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    // Define base directory for storage (e.g., inside the project root)
    private final String UPLOAD_DIRECTORY = "uploads/task_attachments/";
    @Transactional
    public AttachmentDTO uploadAttachment(Integer taskId, Integer userId, MultipartFile file) throws IOException {
        // validate entities exist
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + taskId + " not found."));
        User uploader = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
        // prepare file path and unique identifier
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        Path targetPath = uploadPath.resolve(uniqueFileName);
        // create directory if it doesn't exist
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new IOException("Failed to create upload directory: " + UPLOAD_DIRECTORY, e);
            }
        }
        // save file to disk
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        // create and save metadata entity
        TaskAttachment attachment = new TaskAttachment();
        attachment.setTask(task);
        attachment.setUploadedBy(uploader);
        attachment.setFileName(originalFileName);
        attachment.setFilePath(targetPath.toString()); // The path on the server
        attachment.setFileSize(file.getSize());
        attachment.setMimeType(file.getContentType());
        attachment.setUploadedAt(LocalDateTime.now());
        TaskAttachment savedAttachment = attachmentRepository.save(attachment);
        // convert to DTO and return
        return attachmentMapper.toDto(savedAttachment);
    }
}
