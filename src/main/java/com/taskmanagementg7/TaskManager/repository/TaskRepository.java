package com.taskmanagementg7.TaskManager.repository;
import com.taskmanagementg7.TaskManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository  extends JpaRepository<Task, Long> {
}
