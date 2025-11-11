package com.taskmanagementg7.TaskManager.mapper;
import com.taskmanagementg7.TaskManager.dtos.RegisterUserRequest;
import com.taskmanagementg7.TaskManager.dtos.UpdateUserRequest;
import com.taskmanagementg7.TaskManager.dtos.UserDTO;
import com.taskmanagementg7.TaskManager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
