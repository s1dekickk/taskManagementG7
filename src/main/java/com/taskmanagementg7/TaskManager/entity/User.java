package com.taskmanagementg7.TaskManager.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id // marks this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//  auto increments the ID
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

}
