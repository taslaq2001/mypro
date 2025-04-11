package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.repository.notificationsRepository;
import com.mypro.mypro.service.notificationsService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api3")
public class notificationsController {
    
    @Autowired notificationsService ntfcService;
    @Autowired notificationsRepository ntfcRepository;
    @Autowired WebController wbcntrlr;

    @PostMapping("/notifications")
    public void notificationsSeen(HttpServletRequest request) {
        String currentUser = wbcntrlr.getCurrentUser(request);
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int i=notificsList.size();
        if (currentUser.startsWith("mngr")){
            for (int h=0;h<i;h++){
                if(notificsList.get(h).getShow_to().equals("managers")||notificsList.get(h).getShow_to().equals(currentUser)){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }else{
            for (int h=0;h<i;h++){
                if(notificsList.get(h).getShow_to().equals(currentUser)){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }
        int j=usersNotifications.size();
        for (int l=0;l<j;l++){
            usersNotifications.get(l).setSeen(true);
            ntfcRepository.save(usersNotifications.get(l));
        }


    }
    
    

    
}