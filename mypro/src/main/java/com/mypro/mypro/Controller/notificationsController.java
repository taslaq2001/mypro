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
        int l=notificsList.size();
        for (int h=0;h<l;h++){
            if(notificsList.get(h).getShow_to().equals(currentUser)){
                notificsList.get(h).setSeen(true);
                ntfcRepository.save(notificsList.get(h));
            }

        }
 
    }
    
    

    
}