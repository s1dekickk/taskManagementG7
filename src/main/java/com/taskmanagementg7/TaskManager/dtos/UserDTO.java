package com.taskmanagementg7.TaskManager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private long id;
    private String email;
    private String name;
}
