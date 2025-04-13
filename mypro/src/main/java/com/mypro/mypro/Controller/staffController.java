package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mypro.mypro.model.staff;
import com.mypro.mypro.model.tasks;
import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.repository.staffRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;
import com.mypro.mypro.service.staffService;
import com.mypro.mypro.service.tasksService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/api2")
public class staffController {

    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;
    @Autowired tasksService tskService;
    @Autowired staffService stfService;
    @Autowired notificationsService ntfcService;
    @Autowired staffService stfservice;
    @Autowired staffRepository stfRepository;
    @Autowired WebController wbcntrlr;




    @GetMapping("/mngr")
    public String mainPage(HttpServletRequest request, Model model, @RequestParam(required = false) Integer curChat,@RequestParam(required = false)Integer delCht) {

        if (!wbcntrlr.validLogin(request)) {return "Welcome";}

        String currentUser=wbcntrlr.getCurrentUser(request);

        model.addAttribute("tasks",tskService.showUsersTasks(request));
        model.addAttribute("staff",stfService.showUsers());
        model.addAttribute("notifications", ntfcService.showUsersNotifics(request));
        model.addAttribute("chats", chtService.showUsersChats(request));
        model.addAttribute("messages", mesgService.showUsersMessages(request));
        model.addAttribute("currentUser", currentUser);

        if (curChat!=null){
            model.addAttribute("currentChat", curChat);
        }

        if (delCht!=null){
            model.addAttribute("delChtTxt", "the other person deleted his chat\ncreate a new one to contact them");
            model.addAttribute("delChtId", delCht);
        }

        return "ManagerOverview";
    } 

    @GetMapping("SignUp.html")
    public String stf(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if(user!=null){
            String currentUser=user.getUsername();
            if (currentUser.startsWith("mngr")) {
                return "SignUp";
            }
        }
        return "Welcome";
        
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam String name, @RequestParam String username, @RequestParam String password,@RequestParam String email,@RequestParam String confirmPassword, Model model) {
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "passwords do not match");
            return "SignUp";
        }
        try{
            stfservice.saveUser(name, username,password, email);
            return"redirect:/api2/mngr";
        }catch(Exception e){
            model.addAttribute("error", "username or email already exists");
            return "SignUp";
        }

    }

    @DeleteMapping("/delete3")
    public String deleteUser(@RequestParam String dltuser){
        staff user=stfRepository.findByUsername(dltuser);
        List <tasks> tsks=tskService.showTasks();
        int j=tsks.size();
        for (int i=0; i<j;i++){
            if(tsks.get(i).getAssigned_to()!=null&&tsks.get(i).getAssigned_to().equals(dltuser)){
                tskService.delete(tsks.get(i));
                j-=1;
            }
        }
        stfservice.delete(user);
        return"redirect:/api2/mngr";
    }

    @GetMapping("Calendar.html")
    public String clndr(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        return "Calendar";
    }
    @GetMapping("/Welcome.html")
    public String logOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "Welcome";
    }


    
}