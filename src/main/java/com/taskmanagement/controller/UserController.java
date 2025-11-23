package com.taskmanagement.controller;

import com.taskmanagement.dtos.user.UserDTO;
import com.taskmanagement.dtos.ChangePasswordRequest;
import com.taskmanagement.dtos.user.RegisterUserRequest;
import com.taskmanagement.dtos.user.UpdateUserRequest;
import com.taskmanagement.mapper.UserMapper;
import com.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Set;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    // GET
    @GetMapping
    public Iterable<UserDTO> listUsers(
            @RequestParam(required = false, defaultValue = "username") String sort
    ) {
        if (!Set.of("username", "email", "fullName", "createdAt").contains(sort)) {
            sort = "username";
        }
        return userService.listUsers(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    // GET /id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        // Delegation to service with better error handling
        UserDTO userDTO = userMapper.toDto(userService.getUserById(id));
        return ResponseEntity.ok(userDTO);
    }

    // POST /users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        var user = userService.createUser(request);
        var userDTO = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDTO.getUserId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }
    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id") int id,
            @Valid @RequestBody UpdateUserRequest request) {
        var user = userService.updateUser(id, request);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Set standard 204 status
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    // POST /users/{id}/change-password
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable int id,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }
}