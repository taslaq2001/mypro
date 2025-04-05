package com.mypro.mypro.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mypro.mypro.model.messages;
import com.mypro.mypro.repository.messagesRepository;

@Service

public class messagesService {

    @Autowired
    private messagesRepository msgRepository;

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
        //Collections.reverse(msgs);
        return msgs;
        
    }
    
}