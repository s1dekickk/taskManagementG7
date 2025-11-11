package com.taskmanagementg7.TaskManager.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//  auto increments the ID
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
