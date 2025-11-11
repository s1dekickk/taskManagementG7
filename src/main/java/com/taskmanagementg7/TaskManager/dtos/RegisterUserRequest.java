package com.taskmanagementg7.TaskManager.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String name;
    private String password;
}
