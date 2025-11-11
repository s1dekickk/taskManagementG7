package com.taskmanagementg7.TaskManager.mapper;
import com.taskmanagementg7.TaskManager.dtos.TaskDTO;
import com.taskmanagementg7.TaskManager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    List<TaskDTO> toDtoList(List<Task> tasks);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userName", ignore = true)
    TaskDTO toDto(Task task);
    Task toEntity(TaskDTO taskDTO);
}
