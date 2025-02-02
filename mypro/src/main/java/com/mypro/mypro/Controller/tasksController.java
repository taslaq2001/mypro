package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api1")

public class tasksController{

    @GetMapping("/tasks")
    public String tsks(){
        return "ToDoList";
    }

    @GetMapping("Calendar.html")
    public String clndr(){
        return ("Calendar");
    }
    @GetMapping("Welcome.html")
    public String logOut(){
        return ("Welcome");
    }
}