package com.taskmanagementg7.TaskManager.controller;
import com.taskmanagementg7.TaskManager.dtos.RegisterUserRequest;
import com.taskmanagementg7.TaskManager.dtos.UpdateUserRequest;
import com.taskmanagementg7.TaskManager.dtos.UserDTO;
import com.taskmanagementg7.TaskManager.entity.Role;
import com.taskmanagementg7.TaskManager.mapper.UserMapper;
import com.taskmanagementg7.TaskManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.ap.shaded.freemarker.core.UnregisteredOutputFormatException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping
    public Iterable<UserDTO> listUsers(
            @RequestParam (required = false,defaultValue = "name") String sort
    ) {
        if(!Set.of("name","email").contains(sort)){
            sort="name";
        }
        return userRepository.findAll(Sort.by(sort))
                             .stream()
                             .map(userMapper::toDto)
                             .toList();
      }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user==null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder){
        var user = userMapper.toEntity(request);
        user.setRole(Role.USER);
        userRepository.save(user);
        var userDTO = userMapper.toDto(user);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable (name="id") Long id,
            @RequestBody UpdateUserRequest request){
            var user = userRepository.findById(id).orElse(null);
            if (user==null){
                return ResponseEntity.notFound().build();
            }
            userMapper.update(request, user);
            userRepository.save(user);
            return ResponseEntity.ok(userMapper.toDto(user));
    }
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if (user==null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}

