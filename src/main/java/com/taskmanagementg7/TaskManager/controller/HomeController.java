package com.taskmanagementg7.TaskManager.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(org.springframework.ui.Model model){
        model.addAttribute("name", "Backend Developer");
        return "index";
    }
}
