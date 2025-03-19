package com.mypro.mypro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.Controller.chatsController;
import com.mypro.mypro.repository.staffRepository;
import com.mypro.mypro.model.staff;

import lombok.AllArgsConstructor;

@Service
public class staffService {

    
    @Autowired staffRepository stfrepository;
    public staff saveUser(String name, String username, String password, String email){ 
        staff user=new staff();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        return stfrepository.save(user);
    }
}