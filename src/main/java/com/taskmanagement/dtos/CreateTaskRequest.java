package com.taskmanagement.dtos;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private String priority;
    private LocalDate startDate;
    private Integer categoryId;
    private Integer creatorId;
    private Set<Integer> tagIds;
}
