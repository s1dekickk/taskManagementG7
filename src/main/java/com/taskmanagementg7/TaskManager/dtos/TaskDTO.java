package com.taskmanagementg7.TaskManager.dtos;
import lombok.Data;

import java.time.LocalDate;
@Data
public class TaskDTO {
    private Long taskId;
    private Long userId;// The ID of the assigned user
    private String userName; // Optional: To display the user's name
    private String priority;
    private LocalDate dueDate;
}
