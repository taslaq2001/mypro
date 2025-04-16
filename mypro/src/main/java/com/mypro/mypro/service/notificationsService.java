package com.mypro.mypro.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.Controller.WebController;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.model.tasks;
import com.mypro.mypro.repository.notificationsRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class notificationsService {

    @Autowired notificationsRepository ntfcrepository;
    @Autowired WebController wbcntrlr;

    public notifications newNotif( String content , String show_to){
        notifications notific=new notifications();
        Date d=new Date(System.currentTimeMillis());
        notific.setContent(content);
        notific.setCreated_at(d);
        notific.setSeen(false);
        notific.setShow_to(show_to);
        return ntfcrepository.save(notific);   
    }

    public List<notifications> showNotifics(){
               
        List<notifications> Notifics = ntfcrepository.findAll();
        Iterator<notifications> iterator = Notifics.iterator();
        while (iterator.hasNext()) {
            notifications notific = iterator.next();
            boolean status= notific.getSeen();
            Date d = notific.getCreated_at();
            Date dA=new Date(System.currentTimeMillis()-(7L * 24 * 60 * 60 * 1000));
            if(d.before(dA)){
                if (status||notific.getShow_to().equals("ANYONE")){
                    iterator.remove();
                    ntfcrepository.delete(notific);
                }

            }

        }
        Collections.reverse(Notifics);
        return Notifics;
    
    }

    public List<notifications> showUsersNotifics(HttpServletRequest request){

        String currentUser=wbcntrlr.getCurrentUser(request);

        List<notifications> notificsList=showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int l=notificsList.size();
        for (int h=0;h<l;h++){
            if(notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser) ){
                usersNotifications.add(notificsList.get(h));
            }

        }

        return usersNotifications;

    }
    
    
}