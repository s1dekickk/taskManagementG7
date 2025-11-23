package com.taskmanagement.dtos.task;
import java.time.LocalDateTime;
import java.util.Set;
import com.taskmanagement.entity.task.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must be under 100 characters.")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters.")
    private String description;

    @NotNull(message = "Priority is required.")
    private TaskPriority priority;

    @FutureOrPresent(message = "Start date cannot be in the past.")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "Due date cannot be in the past.")
    @NotNull(message = "Due date is required.")
    private LocalDateTime dueDate;

    @NotNull(message = "Category ID is required.")
    private Integer categoryId; // Use ID for relationship mapping

    private Set<Integer> tagIds;

    private Set<Integer> assignedUserIds;
}