package com.taskmanagement.mapper;
import com.taskmanagement.dtos.RegisterUserRequest;
import com.taskmanagement.dtos.UpdateUserRequest;
import com.taskmanagement.dtos.UserDTO;
import com.taskmanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
