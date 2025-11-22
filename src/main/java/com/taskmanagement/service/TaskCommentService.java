package com.taskmanagement.service;
import com.taskmanagement.dtos.comment.CommentDTO;
import com.taskmanagement.entity.task.Task;
import com.taskmanagement.entity.TaskComment;
import com.taskmanagement.entity.user.User;
import com.taskmanagement.mapper.TaskCommentMapper;
import com.taskmanagement.repository.TaskCommentRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class TaskCommentService {
    private final TaskCommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskCommentMapper commentMapper;
    public List<CommentDTO> getCommentsByTaskId(Integer taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task with ID " + taskId + " not found.");
        }
        // Use the custom repository method to fetch all comments for the task, ordered correctly
        List<TaskComment> comments = commentRepository.findByTask_TaskIdOrderByCreatedAtAsc(taskId);
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO addComment(Integer taskId, Integer userId, String text, Integer parentCommentId) {
        // validate mandatory entities
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + taskId + " not found."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        // validate parent comment for replies
        TaskComment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent Comment with ID " + parentCommentId + " not found."));

            // ensure the reply belongs to the same task (or reject the operation)
            if (!parentComment.getTask().getTaskId().equals(taskId)) {
                throw new IllegalArgumentException("Parent comment does not belong to the specified task.");
            }
        }

        // create and save the new comment entity
        TaskComment newComment = new TaskComment();
        newComment.setTask(task);
        newComment.setUser(user);
        newComment.setText(text);
        newComment.setParentComment(parentComment);
        newComment.setCategory("Commented");//default
        TaskComment savedComment = commentRepository.save(newComment);

        // map and return
        return commentMapper.toDto(savedComment);
    }
}
