package com.mypro.mypro.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.Controller.WebController;
import com.mypro.mypro.model.chats;
import com.mypro.mypro.repository.chatsRepository;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class chatsService {

    @Autowired chatsRepository chtRepository;
    @Autowired WebController wbcntrlr;

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

    public List<chats> showUsersChats(HttpServletRequest request){
        String currentUser = wbcntrlr.getCurrentUser(request);
        List<chats> chts=showChats();
        List<chats> usersChats=new ArrayList<>();
        int m=chts.size();
        for (int n=0;n<m;n++){
            if(!chts.get(n).getDeleted_by().equals(currentUser)){
                if(chts.get(n).getFirst_person().equals(currentUser)||chts.get(n).getSecond_person().equals(currentUser)){
                    usersChats.add(chts.get(n));
                }
           }
        }
        return usersChats;
    }



    
}