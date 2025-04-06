package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.model.chats;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.model.staff;
import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.staffService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api4")
public class chatsController {

    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;
    @Autowired staffService stfService;


    
    @PostMapping("/chatting2")
    public String createChat(@RequestParam String receiver, HttpServletRequest request, Model model) {
        model.addAttribute("staff",stfService.showUsers());
        staff user = (staff) request.getSession().getAttribute("staff");
        String sender=user.getUsername();
        chats cht=new chats();
        cht.setFirst_person(sender);
        cht.setSecond_person(receiver);
        cht.setDeleted_by("NONE");
        chtRepository.save(cht);
        
        if (user!=null){
            request.getSession().setAttribute("staff",user);
            if (user.getUsername().startsWith("mngr")){
                return "redirect:/api2/mngr";
            }
                
            
        }
        if (user!=null){
            request.getSession().setAttribute("staff",user);
            if (user.getUsername().startsWith("mngr")){
                return "redirect:/api2/mngr";
            }
                
            
        }
        return "redirect:/api1/tasks";

        
    }
        
    @GetMapping("/chatting2")
    public String createChat2(@RequestParam String receiver, HttpServletRequest request, Model model) {
        model.addAttribute("staff",stfService.showUsers());
        staff user = (staff) request.getSession().getAttribute("staff");
        String sender=user.getUsername();
        
        chats cht=new chats();
        cht.setFirst_person(sender);
        cht.setSecond_person(receiver);
        cht.setDeleted_by("NONE");
        chtRepository.save(cht);
        if (user!=null){
            request.getSession().setAttribute("staff",user);
            if (user.getUsername().startsWith("mngr")){
                return "redirect:/api2/mngr";
            }
                
            
        }
        return "redirect:/api1/tasks";
  

        
    }

    
    
    


    
}