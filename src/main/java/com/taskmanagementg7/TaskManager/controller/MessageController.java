package com.taskmanagementg7.TaskManager.controller;

import com.taskmanagementg7.TaskManager.entity.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hello")
    public Message sayHello(){
        return new Message("Hello World");
    }
}
