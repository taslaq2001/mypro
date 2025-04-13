package com.mypro.mypro.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.Controller.WebController;
import com.mypro.mypro.model.notifications;
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
        int i = Notifics.size();
        for (int j=0; j<i;j++){
            notifications notific=Notifics.get(j);
            boolean status= notific.getSeen();
            Date d = notific.getCreated_at();
            Date dA=new Date(System.currentTimeMillis()-(10L * 24 * 60 * 60 * 1000));
            if(d.before(dA)){
                if (status==true||notific.getShow_to().equals("ANYONE") ){
                    Notifics.remove(notific);
                    ntfcrepository.delete(notific);
                    i=-1;
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