package com.taskmanagement.service;

import com.taskmanagement.dtos.ActivityLogDTO;
import com.taskmanagement.entity.activity.ActivityLog;
import com.taskmanagement.mapper.ActivityLogMapper;
import com.taskmanagement.repository.ActivityLogRepository;
import com.taskmanagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final TaskRepository taskRepository;
    private final ActivityLogMapper activityLogMapper;

    @Transactional(readOnly = true)
    public List<ActivityLogDTO> getTaskActivity(Integer taskId, int limit) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task with ID " + taskId + " not found.");
        }
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<ActivityLog> logs = activityLogRepository.findByTask_TaskId(taskId, pageable).getContent();
        return activityLogMapper.toDtoList(logs);
    }
}
