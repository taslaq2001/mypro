package com.mypro.mypro.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class WebController {
        @GetMapping("/")
    public String home() {
        return "Welcome";
    }
    
}
