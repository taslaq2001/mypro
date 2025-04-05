package com.mypro.mypro.Controller;

import com.mypro.mypro.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;


import com.mypro.mypro.service.staffService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {
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
    @Autowired
    private staffService stfservice;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, HttpServletRequest request, Model model) {
  
            staff user = stfservice.validateLogin(username, password, request);
            
            
            if (user!=null){
                request.getSession().setAttribute("staff",user);
                if (user.getUsername().startsWith("mngr")){
                    return "redirect:/api2/mngr";
                }else{
                    return "redirect:/api1/tasks";
                }
            }else{
                model.addAttribute("error", "username or password incorrect");
                return "Welcome.html";
            }

    }


     @GetMapping("/")
    public String welcome(){
        return ("Welcome");
    }

}    
