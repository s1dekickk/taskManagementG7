package com.taskmanagement.repository;
import com.taskmanagement.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Integer> {
    List<TaskComment> findByTask_TaskIdOrderByCreatedAtAsc(Integer taskId);
}
