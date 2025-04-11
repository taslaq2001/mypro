package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mypro.mypro.model.messages;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.repository.notificationsRepository;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/api5")
class messagesController {

    @Autowired messagesRepository msgRepository;
    @Autowired messagesService msgService;
    @Autowired notificationsRepository ntfcRepository;
    @Autowired notificationsService ntfcService;
    @Autowired WebController wbcntrlr;
  
    @PostMapping("/send/{id}/{fperson}/{sperson}")   
    public String sendMessage(@PathVariable("id") Integer id, @PathVariable("fperson") String fperson,@PathVariable("sperson") String sperson, @RequestParam("messageText") String messageText, HttpServletRequest request, Model model) {
        String currentUser=wbcntrlr.getCurrentUser(request);
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
        return wbcntrlr.mngrOrUser(request); 
        
             
    }

}
