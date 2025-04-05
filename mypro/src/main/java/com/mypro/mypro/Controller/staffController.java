package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypro.mypro.model.chats;
import com.mypro.mypro.model.messages;
import com.mypro.mypro.model.notifications;
import com.mypro.mypro.model.staff;
import com.mypro.mypro.model.tasks;
import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;
import com.mypro.mypro.service.staffService;
import com.mypro.mypro.service.tasksService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.angus.mail.handlers.message_rfc822;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import com.mypro.mypro.Controller.*;


@Controller
@RequestMapping("/api2")
public class staffController {

    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;

    @GetMapping("SignUp.html")
    public String stf(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user != null) {
            return "SignUp";
        }
        return"redirect: /api2/mngr";
        
    }
    @Autowired
    private tasksService tskService;
    @Autowired staffService stfService;
    @Autowired
    private notificationsService ntfcService;
    @GetMapping("/mngr")
    public String mainPage(HttpServletRequest request, Model model) {
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        String currentUser=user.getUsername();
        List<tasks> allTasks=tskService.showTasks();
        int f=allTasks.size();
        for (int g=0;g<f;g++){
            if (allTasks.get(g).getPrivacy()!=null && allTasks.get(g).getPrivacy() && currentUser !=allTasks.get(g).getAssigned_by()){
                allTasks.remove(allTasks.get(g));
                f-=1;
            }
        }
  
        model.addAttribute("tasks", allTasks);

        model.addAttribute("staff",stfService.showUsers());
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int l=notificsList.size();
        for (int h=0;h<l;h++){
            if(notificsList.get(h).getShow_to().equals("managers") || notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser)){
                usersNotifications.add(notificsList.get(h));
            }
        }
        
        model.addAttribute("notifications", usersNotifications);

        List<chats> chts=chtService.showChats();
        List<chats> usersChats=new ArrayList<>();
        int m=chts.size();
        for (int n=0;n<m;n++){
            if(chts.get(n).getFirst_person().equals(currentUser)||chts.get(n).getSecond_person().equals(currentUser)){
                usersChats.add(chts.get(n));
            }
        }
        model.addAttribute("chats", usersChats);

        List<messages> msgs=mesgService.showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }
        model.addAttribute("messages", usersMsgs);
        model.addAttribute("currentUser", currentUser);
        /*
        Set<Integer> ids=new HashSet<>();
        
        for (int y=0; y<usersMsgs.size();y++){
            Integer x=usersMsgs.get(y).getChat_id();
            
            ids.add(x);

            
        }
        for ( Integer i : ids){
            String j=i.toString();
            Set<messages> msgsWids=new HashSet<>();
            for (messages msg : usersMsgs){
                if (msg.getChat_id()==i){
                    msgsWids.add(msg);
                }
            }
            model.addAttribute("msgs"+j, msgsWids);
            System.out.println(currentUser);
        }
*/
        
        

        return "ManagerOverview";
    }  

    @Autowired 
    private staffService stfservice;
    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String name, @RequestParam String username, @RequestParam String password,@RequestParam String email,@RequestParam String confirmPassword, Model model) {
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "passwords do not match");
            return "SignUp.html";
        }
        try{
            stfservice.saveUser(name, username,password, email);
            return"redirect:/api2/mngr";
        }catch(Exception e){
            model.addAttribute("error", "username or email already exists");
            return "SignUp.html";
        }

    }
    

/*
     @GetMapping("/login")
    public String wlcm(){
        return "Welcome";
    }
 */

    @GetMapping("/manage")
    public String mng(Model model, HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        String currentUser=user.getUsername();

        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int l=notificsList.size();
        for (int h=0;h<l;h++){
            if(notificsList.get(h).getShow_to().equals("managers")||notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser)){
                usersNotifications.add(notificsList.get(h));
            }
        }
        model.addAttribute("notifications", usersNotifications);
        List<chats> chts=chtService.showChats();
        List<chats> usersChats=new ArrayList<>();
        int m=chts.size();
        for (int n=0;n<m;n++){
            if(chts.get(n).getFirst_person().equals(currentUser)){
                usersChats.add(chts.get(n));
            }
        }
        model.addAttribute("chats", usersChats);

        List<messages> msgs=mesgService.showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }

        
        
        return "ManagerOverview";
    }

    @GetMapping("Calendar.html")
    public String clndr(){
        return ("Calendar");
    }
    @GetMapping("/Welcome.html")
    public String logOut(HttpServletRequest request){
        request.getSession().invalidate();
        return ("Welcome");
    }
    /* 
    @Autowired
    private staffService stfservice2;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, Model model) throws NullPointerException {
        try {
            stfservice2.loginStaff(username, password);
            return "redirect:/api1/tasks";
        } catch (NullPointerException e) {
            // If login fails, add an error message and show the login page again
            model.addAttribute("error", "Invalid username or password.");
            return "Welcome"; 
        }
    }
    */
    /* 
    @Autowired
    private staffService stfservice2;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {
        try {
            staff user = stfservice2.validateLogin(username, password, request);
            if (user!=null){
                request.getSession().setAttribute("staff",user);
                return "redirect:/api1/tasks";
            }
        }catch (Exception e) {
            return "Welcome";
        }
        return "Welcome"; 
    }
        */

    
}