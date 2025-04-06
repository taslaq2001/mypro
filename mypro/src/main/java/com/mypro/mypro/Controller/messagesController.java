package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.model.messages;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.model.staff;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.repository.notificationsRepository;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api5")
class messagesController {

    @Autowired messagesRepository msgRepository;
    @Autowired messagesService msgService;
    @Autowired notificationsRepository ntfcRepository;
    @Autowired notificationsService ntfcService;
  
    @PostMapping("/send/{id}/{fperson}/{sperson}")   
    public String sendMessage(@PathVariable("id") Integer id, @PathVariable("fperson") String fperson,@PathVariable("sperson") String sperson, @RequestParam("messageText") String messageText, HttpServletRequest request, Model model) {
        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser = user.getUsername();
        if (fperson.equals(currentUser)){
            String receiver= sperson;
            msgService.newMessage(id, currentUser, receiver, messageText);
            notifications newNotific=ntfcService.newNotif("new message from : "+ currentUser, receiver);
            ntfcRepository.save(newNotific);

        }else{
            String receiver= fperson;
            msgService.newMessage(id, currentUser, receiver, messageText);
            notifications newNotific=ntfcService.newNotif("new message from : "+ currentUser, receiver);
            ntfcRepository.save(newNotific);

        }
        List<messages> msgs=msgService.showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }
        model.addAttribute("messages", usersMsgs);

        if (currentUser.startsWith("mngr")){
        return "redirect:/api2/mngr"; 
        }else{
            return "redirect:/api1/tasks";
        } 
        
             
    }
    
    @GetMapping("/send/{id}/{fperson}/{sperson}")    
    public String sendMessage2(@PathVariable("id") Integer id, @PathVariable("fperson") String fperson,@PathVariable("sperson") String sperson, @RequestParam("messageText") String messageText, HttpServletRequest request, Model model) {
        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser = user.getUsername();
        if (fperson.equals(currentUser)){
            String receiver= sperson;
            msgService.newMessage(id, currentUser, receiver, messageText);

        }else{
            String receiver= fperson;
            msgService.newMessage(id, currentUser, receiver, messageText);

        }
        List<messages> msgs=msgService.showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }
        model.addAttribute("messages", usersMsgs);
        if (currentUser.startsWith("mngr")){
            return "redirect:/api2/mngr"; 
            }else{
                return "redirect:/api1/tasks";
            } 
        
        
       
    }

    @GetMapping("/chatsOverviews/{id}") 
    public String chatsView(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser = user.getUsername();
        List<messages> allMessages = msgService.showMessages();
        List<messages> usersMessages = new ArrayList<>();

        for (messages msg : allMessages) {
            if (msg.getChat_id().equals(id)) {
                usersMessages.add(msg);
            }
        }
        model.addAttribute("UsersMessages", usersMessages);
        model.addAttribute("currentUser", currentUser);
       return "Messages";

          
    }
    @PostMapping("/chatsOverviews/{id}") 
    public String chatsView2(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser = user.getUsername();
        List<messages> allMessages = msgService.showMessages();
        List<messages> usersMessages = new ArrayList<>();

        for (messages msg : allMessages) {
            if (msg.getChat_id().equals(id)) {
                usersMessages.add(msg);
            }
        }
        model.addAttribute("UsersMessages", usersMessages);
        model.addAttribute("currentUser", currentUser);
        return "Messages";

          
    }
}
