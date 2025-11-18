package com.taskmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who posted the comment
    @Column(name = "comment_text", nullable = false, length = 2000)
    private String text;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    // --- Threading/Reply Logic ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id") // Links to another comment in the same table
    @JsonIgnore
    private TaskComment parentComment;
    @Column(name = "category", length = 50)
    private String category;
}
