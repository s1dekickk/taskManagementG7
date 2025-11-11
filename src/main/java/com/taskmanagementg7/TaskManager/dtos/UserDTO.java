package com.taskmanagementg7.TaskManager.dtos;

import com.taskmanagementg7.TaskManager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private long id;
    private String email;
    private String name;
}
