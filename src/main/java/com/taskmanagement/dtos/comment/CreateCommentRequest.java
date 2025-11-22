package com.taskmanagement.dtos.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCommentRequest {
    @JsonProperty("text")
    private String text;
    @JsonProperty("parent_comment_id")
    private Integer parentCommentId; //optional, for replies
    @JsonProperty("category")
    private String category;
}
