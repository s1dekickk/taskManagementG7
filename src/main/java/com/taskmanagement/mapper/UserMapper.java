package com.taskmanagement.mapper;
import com.taskmanagement.dtos.user.RegisterUserRequest;
import com.taskmanagement.dtos.user.UpdateUserRequest;
import com.taskmanagement.dtos.user.UserDTO;
import com.taskmanagement.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "status", source = "user.status")
    @Mapping(target = "role", source = "user.role")
    UserDTO toDto(User user);
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterUserRequest request);
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(UpdateUserRequest request, @MappingTarget User user);
}
