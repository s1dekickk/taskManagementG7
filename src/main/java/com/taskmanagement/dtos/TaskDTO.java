package com.taskmanagement.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("task_id")
    private Integer taskId;
    private String description;
    private String userName;
    private Integer userId;
    private String status;
    private String priority;
    private LocalDateTime createdAt;
    @JsonProperty("is_trashed")
    private boolean isDeleted;
    @JsonProperty("completed_date")
    private LocalDateTime completedDate;
    @JsonProperty("category")
    private String categoryName;
    private LocalDate dueDate;
}
