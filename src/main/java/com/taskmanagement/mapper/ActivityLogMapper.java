package com.taskmanagement.mapper;

import com.taskmanagement.dtos.ActivityLogDTO;
import com.taskmanagement.entity.activity.ActivityLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActivityLogMapper {
    @Mapping(target = "taskId", source = "log.task.taskId")
    @Mapping(target = "userId", source = "log.user.userId")
    ActivityLogDTO toDto(ActivityLog log);

    List<ActivityLogDTO> toDtoList(List<ActivityLog> logs);
}