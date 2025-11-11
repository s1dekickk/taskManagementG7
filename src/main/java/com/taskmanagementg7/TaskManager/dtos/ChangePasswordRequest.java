package com.taskmanagementg7.TaskManager.dtos;

import lombok.Data;
@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
