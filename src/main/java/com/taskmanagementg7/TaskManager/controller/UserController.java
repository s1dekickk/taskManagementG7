package com.taskmanagementg7.TaskManager.controller;
import com.taskmanagementg7.TaskManager.dtos.UserDTO;
import com.taskmanagementg7.TaskManager.mapper.UserMapper;
import com.taskmanagementg7.TaskManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public UserDTO createUser(@RequestBody UserDTO data){
       return data;
    }
}

