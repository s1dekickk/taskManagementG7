package com.taskmanagement.dtos;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String priority;
    private LocalDate startDate;
    private Integer categoryId;
    private Set<Integer> tagIds;
}
