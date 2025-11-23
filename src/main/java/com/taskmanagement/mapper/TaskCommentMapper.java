package com.taskmanagement.mapper;
import com.taskmanagement.dtos.comment.CommentDTO;
import com.taskmanagement.dtos.comment.CommentUserDTO;
import com.taskmanagement.entity.user.TaskComment;
import com.taskmanagement.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface TaskCommentMapper {
    default CommentUserDTO toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return CommentUserDTO.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .build();
    }
    @Mapping(target = "taskId", source = "task.taskId")
    @Mapping(target = "parentCommentId", source = "parentComment.commentId")
    CommentDTO toDto(TaskComment comment);
}
