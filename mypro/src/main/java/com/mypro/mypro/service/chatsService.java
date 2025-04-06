package com.mypro.mypro.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mypro.mypro.model.chats;
import com.mypro.mypro.repository.chatsRepository;

@Service
public class chatsService {

    @Autowired
    private chatsRepository chtRepository;

    public chats newChat(String first_person, String second_person){
        chats cht=new chats();
        cht.setFirst_person(first_person);
        cht.setSecond_person(second_person);
        cht.setDeleted_by("NONE");
        return chtRepository.save(cht);
    }

    public List<chats> showChats(){
        List<chats>chts=chtRepository.findAll();
        Collections.reverse(chts);
        return chts;
    }

    public void deleteChat(chats cht){
        chtRepository.delete(cht);
    }

    
}