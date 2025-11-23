package com.taskmanagement.dtos.assignments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Builder;


@Data
@Builder
public class AssignmentDTO {
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("full_name")
    private String fullName;
}

