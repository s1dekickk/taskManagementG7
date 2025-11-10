package com.taskmanagementg7.TaskManager.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key

    private LocalDate assignedDate;

    // relationship to Task
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // relationship to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User assignedMember;

    // constructors, setters and getters
}