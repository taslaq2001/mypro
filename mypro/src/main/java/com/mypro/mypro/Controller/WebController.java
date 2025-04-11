package com.mypro.mypro.Controller;
import com.mypro.mypro.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.mypro.mypro.service.staffService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    @Autowired staffService stfservice;

    @GetMapping("/")
    public String welcome(){
        return "Welcome";
    }

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
            return "Welcome";
        }

    }

    public String getCurrentUser( HttpServletRequest request) {
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }else{
            String currentUser=user.getUsername();
            return currentUser;
        }

    }

    public boolean validLogin(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user != null) {
            return true;
        }else{
            return false;
        }

    }

    public String mngrOrUser(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");       
        if (user!=null){
            request.getSession().setAttribute("staff",user);
            if (user.getUsername().startsWith("mngr")){
                return "redirect:/api2/mngr";
            }else{
                return "redirect:/api1/tasks";
            }
   
        }else{
            return "Welcome";
        }
    }




}    
