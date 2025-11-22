package com.taskmanagement.dtos.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentUserDTO {
    //this hold public user info needed to comment display
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("full_name")
    private String fullName;
}
