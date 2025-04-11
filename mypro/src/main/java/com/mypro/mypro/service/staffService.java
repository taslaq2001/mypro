package com.mypro.mypro.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.mypro.mypro.repository.staffRepository;
import jakarta.servlet.http.HttpServletRequest;
import com.mypro.mypro.model.staff;


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
 
    public List<staff> showUsers(){
            
        List<staff> allUsers = stfrepository.findAll();
        
        return allUsers;
            
    }
    public staff validateLogin(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {
            staff user=stfrepository.findByUsername(username);
            if (user == null || !password.equals(user.getPassword())) {
                return null;
            }

            request.getSession().setAttribute("staff", user);
            return user;

    }

    public List<staff>showManagers(){
        List<staff> allStaff=stfrepository.findAll();
        List<staff> managers=new ArrayList<>();
        int i = allStaff.size();
        for (int j=0; j<i;j++){
            if(allStaff.get(j).getUsername().startsWith("mngr")){
                managers.add(allStaff.get(j));
            }
        }
        return managers;
    }

    public void delete(staff user){
        stfrepository.delete(user);
    }
    

}