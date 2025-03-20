package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.repository.staffRepository;
import com.mypro.mypro.service.staffService;

import jakarta.servlet.http.HttpServletRequest;
import com.mypro.mypro.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api1")

public class tasksController{

    /* 
    @GetMapping("/tasks")
    public String tsks(){
        return "ToDoList";
    }
    */
    @GetMapping("/tasks")
    public String mainPage(HttpServletRequest request) {
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        return "ToDoList";
    }    
  
    
    /* 
    @Autowired
    private staffService stfservice;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, Model model) throws NullPointerException {
        try {
            stfservice.loginStaff(username, password);
            return "redirect:/api1/tasks";
        } catch (NullPointerException e) {
            // If login fails, add an error message and show the login page again
            model.addAttribute("error", "Invalid username or password.");
            return "Welcome"; 
        }
    
    }
    */
    /* 
    @Autowired
    private staffService stfservice;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {
        try {
            staff user = stfservice.validateLogin(username, password, request);
            if (user!=null){
                request.getSession().setAttribute("staff",user);
                return "redirect:/api1/tasks";
            }
        }catch (Exception e) {
            return "Welcome";
        }
        return "Welcome"; 
    
    }
        */




    @GetMapping("Calendar.html")
    public String clndr(){
        return ("Calendar");
    }
    @GetMapping("/Welcome.html")
    public String logOut(HttpServletRequest request){
        request.getSession().invalidate();
        return ("Welcome");
    }

}