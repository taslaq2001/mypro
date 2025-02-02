package com.mypro.mypro.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class WebController {
        @GetMapping("/")
    public String home() {
        return "Welcome to MyPro!";
    }
    
}
