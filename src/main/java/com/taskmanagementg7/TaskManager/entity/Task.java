package com.taskmanagementg7.TaskManager.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    // Relationships
    // The "assigned_member" attribute from the schema is handled by the Assignment entity relationship.
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    private List<Assignment> assignments;
    // constructors setters and getters
}