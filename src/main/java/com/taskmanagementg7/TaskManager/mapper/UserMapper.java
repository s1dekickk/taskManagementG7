package com.taskmanagementg7.TaskManager.mapper;
import com.taskmanagementg7.TaskManager.dtos.RegisterUserRequest;
import com.taskmanagementg7.TaskManager.dtos.UserDTO;
import com.taskmanagementg7.TaskManager.entity.User;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(RegisterUserRequest request);
}
