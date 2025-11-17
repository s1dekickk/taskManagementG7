package com.taskmanagement.dtos;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String title;
    private Long taskId;
    private String description;
    private String userName;
    private Long userId;
    private String status;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
}
