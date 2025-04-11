package com.mypro.mypro.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.Controller.WebController;
import com.mypro.mypro.model.messages;
import com.mypro.mypro.repository.messagesRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service

public class messagesService {

    @Autowired messagesRepository msgRepository;
    @Autowired WebController wbcntrlr;

    public messages newMessage(int chat_id, String sender, String receiver, String message){
        messages msg= new messages();
        msg.setChat_id(chat_id);
        msg.setMessage(message);
        msg.setReceiver(receiver);
        msg.setSender(sender);
        LocalDateTime created_at=LocalDateTime.now();
        msg.setCreated_at(created_at);
        return msgRepository.save(msg);
    }

    public void deleteMessage(messages msg){
        msgRepository.delete(msg);
    }

    public List<messages> showMessages(){
        List<messages>msgs=msgRepository.findAll();
        return msgs;
        
    }

    public List<messages> showUsersMessages(HttpServletRequest request){
        String currentUser = wbcntrlr.getCurrentUser(request);
        List<messages> msgs=showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }
        return usersMsgs;
    }
    
}