package com.taskmanagement.repository;
import com.taskmanagement.entity.TaskAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Integer> {
}
