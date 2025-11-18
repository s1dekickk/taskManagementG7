package com.taskmanagement.mapper;

import com.taskmanagement.dtos.CommentDTO;
import com.taskmanagement.dtos.CommentUserDTO;
import com.taskmanagement.entity.TaskComment;
import com.taskmanagement.entity.User;
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
