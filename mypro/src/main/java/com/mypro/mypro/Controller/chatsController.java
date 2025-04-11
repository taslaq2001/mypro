package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mypro.mypro.model.chats;
import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.staffService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api4")
public class chatsController {

    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;
    @Autowired staffService stfService;
    @Autowired WebController wbcntrlr;


    
    @GetMapping("/chatting2")
    public String createChat(@RequestParam String receiver, HttpServletRequest request, Model model) {
        model.addAttribute("staff",stfService.showUsers());
        chtService.newChat(receiver, wbcntrlr.getCurrentUser(request));
        return wbcntrlr.mngrOrUser(request); 
    }

    
}