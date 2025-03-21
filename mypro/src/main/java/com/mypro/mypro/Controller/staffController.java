package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.model.staff;
import com.mypro.mypro.service.staffService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api2")
public class staffController {

    @GetMapping("/staff")
    public String stf(){
        return "SignUp";
    }
    @Autowired 
    private staffService stfservice;
    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String name, @RequestParam String username, @RequestParam String password,@RequestParam String email) {
        stfservice.saveUser(name, username,password, email);
        return "redirect:/api2/login";
    }
    

/*
     @GetMapping("/login")
    public String wlcm(){
        return "Welcome";
    }
 */

    @GetMapping("/manage")
    public String mng(){
        return "ManagerOverview";
    }

    @GetMapping("Calendar.html")
    public String clndr(){
        return ("Calendar");
    }
    /* 
    @Autowired
    private staffService stfservice2;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, Model model) throws NullPointerException {
        try {
            stfservice2.loginStaff(username, password);
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
    private staffService stfservice2;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {
        try {
            staff user = stfservice2.validateLogin(username, password, request);
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

    
}