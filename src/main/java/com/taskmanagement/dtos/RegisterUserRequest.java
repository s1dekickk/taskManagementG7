package com.taskmanagement.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String name;
    private String password;
}
