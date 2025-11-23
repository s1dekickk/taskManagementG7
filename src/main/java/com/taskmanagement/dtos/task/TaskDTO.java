package com.taskmanagement.dtos.task;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskmanagement.entity.task.TaskStatus;
import com.taskmanagement.entity.task.TaskPriority;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TaskDTO {

    // System/Read-Only Fields
    @JsonProperty("task_id")
    private Integer taskId;

    // Core Task Fields
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("due_date")
    private LocalDateTime dueDate;

    // Denormalized Creator Info
    @JsonProperty("creator_id") // Renamed for accuracy
    private Integer creatorId;

    @JsonProperty("creator_username")
    private String creatorUsername;

    // Denormalized Relationship Info
    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("tag_names")
    private Set<String> tagNames;

    // Relational ID Sets
    @JsonProperty("assigned_user_ids")
    private Set<Integer> assignedUserIds;

    // Timestamps
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
