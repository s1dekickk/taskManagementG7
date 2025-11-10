package com.taskmanagementg7.TaskManager.dtos;
import lombok.Data;

@Data
public class TaskDTO {
    private Long taskId;
    private Long userId; // The ID of the assigned user
    private String userName; // Optional: To display the user's name
}
