package com.taskmanagement.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ActivityLogDTO {
    @JsonProperty("log_id")
    private Integer logId;

    @JsonProperty("task_id")
    private Integer taskId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("action_type")
    private String actionType; // e.g., "CREATED", "UPDATED_STATUS", "DELETED"

    @JsonProperty("details")
    private String details; // Descriptive text about the change

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
