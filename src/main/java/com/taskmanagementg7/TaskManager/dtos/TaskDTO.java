package com.taskmanagementg7.TaskManager.dtos;
import lombok.Data;

import java.time.LocalDate;
@Data
public class TaskDTO {
    private String title;
    private Long taskId;
    private String description;
    private String userName;
    private Long userId;
    private String status;
    private String priority;
    private LocalDate dueDate;
}
