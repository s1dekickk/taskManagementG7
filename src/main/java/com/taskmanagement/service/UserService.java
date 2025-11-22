package com.taskmanagement.service;
import com.taskmanagement.dtos.ChangePasswordRequest;
import com.taskmanagement.dtos.user.RegisterUserRequest;
import com.taskmanagement.dtos.user.UpdateUserRequest;
import com.taskmanagement.entity.user.Role;
import com.taskmanagement.entity.user.User;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public User createUser(RegisterUserRequest request) {
        // Basic check for existing username/email should go here before mapping/saving
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MEMBER);
        user.setStatus(User.UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    public List<User> listUsers(Sort sort) {
        return userRepository.findAll(sort);
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    public User updateUser(Integer userId, UpdateUserRequest request) {
        User user = getUserById(userId);

        userMapper.update(request, user);

        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        User user = getUserById(userId); // Checks if user exists, throws 404 if not.
        userRepository.delete(user);
    }

    public void changePassword(Integer userId, ChangePasswordRequest request) {
        User user = getUserById(userId);

        // 1. COMPARE old raw password with stored hash (CRITICAL SECURITY STEP)
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            // Throw IllegalArgumentException, which can be mapped to a 400 Bad Request
            throw new IllegalArgumentException("Incorrect old password provided.");
        }

        // 2. HASH and set the new password
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
