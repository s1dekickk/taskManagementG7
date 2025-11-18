package com.taskmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("user_id")
    private Integer userId;
    private String username;
    private String email;
    @JsonProperty("full_name")
    private String fullName;
    private String role;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("is_active")
    private Boolean isActive;
}
