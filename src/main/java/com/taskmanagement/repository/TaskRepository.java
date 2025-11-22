package com.taskmanagement.repository;
import com.taskmanagement.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {
    List<Task> findByDeletedTrue();
    List<Task> findByDeletedFalse(Sort sort); ;
}
