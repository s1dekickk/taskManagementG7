package com.taskmanagement.dtos.assignments;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class AssignUsersRequest {
    @NotNull(message = "A list of user IDs to assign is required.")
    @JsonProperty("user_ids")
    private List<Integer> userIds;
}
