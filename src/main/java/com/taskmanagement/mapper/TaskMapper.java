package com.taskmanagement.mapper;
import com.taskmanagement.dtos.task.TaskDTO;
import com.taskmanagement.dtos.task.CreateTaskRequest;
import com.taskmanagement.dtos.task.UpdateTaskRequest;
import com.taskmanagement.entity.task.Task;
import com.taskmanagement.entity.task.Tag;
import com.taskmanagement.entity.TaskAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    @Mapping(source = "createdBy.userId", target = "creatorId")
    @Mapping(source = "createdBy.username", target = "creatorUsername")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "tagNames", expression = "java(mapTagSetToStringSet(task.getTags()))")
    @Mapping(target = "assignedUserIds", expression = "java(mapAssignmentsToUserIds(task.getTaskAssignments()))")
    TaskDTO toDto(Task task);

    List<TaskDTO> toDtoList(List<Task> tasks);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "taskAssignments", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Task toEntity(CreateTaskRequest request);

    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "taskAssignments", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "activities", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void update(UpdateTaskRequest request, @MappingTarget Task task);

    default Set<String> mapTagSetToStringSet(Set<Tag> tags) {
        if (tags == null) return Collections.emptySet();
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
    default Set<Integer> mapAssignmentsToUserIds(Set<TaskAssignment> assignments) {
        if (assignments == null) return Collections.emptySet();
        return assignments.stream()
                .map(assignment -> assignment.getUser().getUserId())
                .collect(Collectors.toSet());
    }
}
