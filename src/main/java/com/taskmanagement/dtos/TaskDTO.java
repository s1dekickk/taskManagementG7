package com.taskmanagement.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskmanagement.entity.TaskStatus;
import com.taskmanagement.entity.TaskPriority;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TaskDTO {

    @JsonProperty("task_id")
    private Integer taskId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private TaskStatus status;

    @JsonProperty("priority")
    private TaskPriority priority;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("due_date")
    private LocalDateTime dueDate;

    // --- Mapped Fields from TaskMapper ---

    // 1. Mapped from 'createdBy.userId'
    @JsonProperty("user_id")
    private Integer userId;

    // 2. Mapped from 'createdBy.fullName'
    @JsonProperty("user_name")
    private String userName;

    // 3. Mapped from Tag Set -> String Set
    @JsonProperty("tag_names")
    private Set<String> tagNames; // <-- FIX: Add this field

    // 4. Mapped from Assignment Set -> User ID Set
    @JsonProperty("assigned_user_ids")
    private Set<Integer> assignedUserIds; // <-- FIX: Add this field

    // 5. Mapped from Category (if you added the mapping)
    @JsonProperty("category_name")
    private String categoryName;

    // --- System Fields ---
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
