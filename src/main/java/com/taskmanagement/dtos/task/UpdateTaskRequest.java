package com.taskmanagement.dtos.task;
import java.time.LocalDateTime;
import java.util.Set;

import com.taskmanagement.entity.task.TaskPriority;
import com.taskmanagement.entity.task.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    @Size(max = 100, message = "Title must be under 100 characters.")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters.")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    @FutureOrPresent(message = "Start date cannot be in the past.")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "Due date cannot be in the past.")
    private LocalDateTime dueDate;

    private Integer categoryId; // change the task's category

    private Set<Integer> tagIds; // replace the entire set of tags

    private Set<Integer> assignedUserIds;
}
