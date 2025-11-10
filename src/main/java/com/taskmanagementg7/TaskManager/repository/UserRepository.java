package com.taskmanagementg7.TaskManager.repository;
import com.taskmanagementg7.TaskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long>{

}
