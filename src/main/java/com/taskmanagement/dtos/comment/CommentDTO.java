package com.taskmanagement.dtos.comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class CommentDTO {
    @JsonProperty("comment_id")
    private Integer commentId;
    @JsonProperty("task_id")
    private Integer taskId;
    @JsonProperty("user") // nest the user details instead of just the ID
    private CommentUserDTO user;
    @JsonProperty("parent_comment_id")
    private Integer parentCommentId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("category")
    private String category;
}
