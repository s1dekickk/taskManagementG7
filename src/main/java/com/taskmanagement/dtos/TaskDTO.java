package com.taskmanagement.dtos;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String title;
    private Integer taskId;
    private String description;
    private String userName;
    private Integer userId;
    private String status;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
}
