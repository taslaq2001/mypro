package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.service.staffService;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String saveUser(@RequestParam String name, String username, String password, String email) {
        //TODO: process POST request
        stfservice.saveUser(name, username,password, email);
        return "redirect:/api2/loggedOut";
    }
    

    @GetMapping("/loggedOut")
    public String wlcm(){
        return "Welcome";
    }

    @GetMapping("/manage")
    public String mng(){
        return "ManagerOverview";
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