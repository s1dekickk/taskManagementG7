package com.taskmanagement.repository;

import com.taskmanagement.entity.activity.ActivityLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer> {
    Page<ActivityLog> findByTask_TaskId(Integer taskId, Pageable pageable);
}
