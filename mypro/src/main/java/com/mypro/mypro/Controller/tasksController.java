package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.repository.notificationsRepository;
import com.mypro.mypro.repository.staffRepository;
import com.mypro.mypro.repository.tasksRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;
import com.mypro.mypro.service.staffService;
import com.mypro.mypro.service.tasksService;

import jakarta.servlet.http.HttpServletRequest;
import com.mypro.mypro.model.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api1")

public class tasksController{
    @Autowired
    private tasksService tskService;
    @Autowired
    private tasksRepository tskrepository;
    @Autowired notificationsService ntfcService;
    @Autowired notificationsRepository ntfcRepository;
    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;


    @GetMapping("/tasks")
    public String mainPage(HttpServletRequest request, Model model) {
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        String currentUser = user.getUsername();
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int k=notificsList.size();
        if (currentUser.startsWith("mngr")){
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("managers")||notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser)){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }else{
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser) ){
                    usersNotifications.add(notificsList.get(h));
                }
            }

        }
  

        
        model.addAttribute("notifications", usersNotifications);
        List<tasks> allTasks=tskService.showTasks();
        List<tasks> usersTasks=new ArrayList<>();
        int i = allTasks.size();
        for (int j=0; j<i;  j++){
            if (allTasks.get(j).getAssigned_to()==null || allTasks.get(j).getAssigned_to().equals(currentUser)){
                usersTasks.add(allTasks.get(j));
                if (allTasks.get(j).getPrivacy()!=null && allTasks.get(j).getPrivacy() && currentUser ==allTasks.get(j).getAssigned_by()){
                    usersTasks.add(allTasks.get(j));
                }
            }

        }
        List<messages> msgs=mesgService.showMessages();
        List<messages> usersMsgs=new ArrayList<>();
        int p=msgs.size();
        for (int q=0;q<p;q++){
            if(msgs.get(q).getSender().equals(currentUser)||msgs.get(q).getReceiver().equals(currentUser)){
                usersMsgs.add(msgs.get(q));
            }
        }
        model.addAttribute("messages", usersMsgs);
        List<chats> chts=chtService.showChats();
        List<chats> usersChats=new ArrayList<>();
        int m=chts.size();
        for (int n=0;n<m;n++){
           if(!chts.get(n).getDeleted_by().equals(currentUser)){
                if(chts.get(n).getFirst_person().equals(currentUser)||chts.get(n).getSecond_person().equals(currentUser)){
                    usersChats.add(chts.get(n));
                }
           }

        }
        model.addAttribute("chats", usersChats);

        model.addAttribute("tasks", usersTasks);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("staff",stfService.showUsers());
        
        
        if (currentUser.startsWith("mngr")){
            return "ManagerOverview";
        }  
            
        return "ToDoList";
    }   




    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id ,HttpServletRequest request) {
        Optional<tasks> taskOptional = tskrepository.findById(id);
        if (taskOptional.isPresent()) {
            tasks taskA = taskOptional.get();
            staff user = (staff) request.getSession().getAttribute("staff");
            if (user.getUsername().startsWith("mngr")){
                tskService.delete(taskA);
                if (taskA.getAssigned_to()!=null && taskA.getAssigned_to()!=user.getUsername()){
                    notifications newNotific=ntfcService.newNotif("one task has been deleted " + taskA.getTitle(), taskA.getAssigned_to());
                    ntfcRepository.save(newNotific);
                }
                return "redirect:/api2/mngr";
            }else{
                if (taskA.getPrivacy()!=null && taskA.getPrivacy()){
                    tskService.delete(taskA);
                    return "redirect:/api1/tasks";
                }
            }
            
        }
  
        return "redirect:/api1/tasks";
    }

    @DeleteMapping("/delete1/{id}")
    public String deleteChat(@PathVariable("id") int id ,HttpServletRequest request) {
        Optional<chats> cht = chtRepository.findById(id);
        if (cht.isPresent()) {
            chats chtA = cht.get();
            staff user = (staff) request.getSession().getAttribute("staff");
            String currentUser=user.getUsername();

            if(chtA.getDeleted_by().equals("NONE")){
                chtA.setDeleted_by(currentUser);
                chtRepository.save(chtA);
            }else{
                List<messages> msgs=mesgService.showMessages();
                for(messages msg : msgs ){
                    if(msg.getChat_id()==id){
                        msgRepository.delete(msg);
                    }
                }
                chtService.deleteChat(chtA);
            }
            

            if (user.getUsername().startsWith("mngr")){
                
    
                return "redirect:/api2/mngr";
            }else{
                
                    
                    return "redirect:/api1/tasks";
                
            }
            
        }
  
        return "redirect:/api1/tasks";
    }
    @PostMapping("change1/{id}")
    public String taskCompleted(HttpServletRequest request,@PathVariable("id") int id,@RequestParam("status") String status, @RequestParam(value = "otherStatus", required = false) String otherStatus) {
        Optional<tasks> task=tskrepository.findById(id);
        if (task.isPresent()) {
            tasks taskA = task.get();

            if ("other".equals(status)) {
                taskA.setStatus(otherStatus);
                Date d=java.sql.Date.valueOf("1111-11-11");
                taskA.setCompleted_on(d);                
            } else {
                taskA.setStatus(status);
                Date d=new Date(System.currentTimeMillis());
                taskA.setCompleted_on(d);
            }
            tskrepository.save(taskA);
            notifications newNotific=ntfcService.newNotif("change in the status of task : "+taskA.getTitle().toString(), "managers");
            ntfcRepository.save(newNotific);

        }
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user.getUsername().startsWith("mngr")){
            return "redirect:/api2/mngr";
        }   
        return "redirect:/api1/tasks";

    }
    @Autowired staffService stfService;
    @PostMapping("/filter")
    public String filter(@RequestParam(name="crtr1", required = false) String crtr1,@RequestParam(name="crtr2", required = false) String crtr2, Model model, HttpServletRequest request) {
        model.addAttribute("staff",stfService.showUsers());
        List<tasks> allTasks=tskService.showTasks();
        List<tasks> filteredTasks=new ArrayList<>();
        if (!crtr1.isEmpty() && !crtr2.isEmpty()){
            int i = allTasks.size();
            for (int j = 0; j<i; j++){
                if(crtr1.equals("ANYONE")){
                    if (crtr2.equals("completed")){
                        if (allTasks.get(j).getAssigned_to()==null && allTasks.get(j).getStatus().equals(crtr2)){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }else if(crtr2.equals("All")){
                        if (allTasks.get(j).getAssigned_to()==null){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }else{
                        if (allTasks.get(j).getAssigned_to()==null && !allTasks.get(j).getStatus().equals("completed")){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }

                }else if (crtr1.equals("All")){
                    if(crtr2.equals("All")){
                        filteredTasks=allTasks;
                    }
                    else if (crtr2.equals("completed")){
                        if (allTasks.get(j).getStatus().equals(crtr2)){
                            filteredTasks.add(allTasks.get(j));
                        }


                    }else{
                        if (!allTasks.get(j).getStatus().equals("completed")){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }


                }else{
                    if (crtr2.equals("completed")){
                        if (allTasks.get(j).getAssigned_to()!=null && allTasks.get(j).getAssigned_to().equals(crtr1) && allTasks.get(j).getStatus().equals(crtr2)){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }else if(crtr2.equals("All")){
                        if (allTasks.get(j).getAssigned_to()!=null && allTasks.get(j).getAssigned_to().equals(crtr1)){
                            filteredTasks.add(allTasks.get(j));
                        }
                    }else{
                        if (allTasks.get(j).getAssigned_to()!=null && allTasks.get(j).getAssigned_to().equals(crtr1) && !allTasks.get(j).getStatus().equals("completed")){
                            filteredTasks.add(allTasks.get(j));
                        }

                    }

                }
            }
        }
        if (crtr1.isEmpty() && !crtr2.isEmpty()){
            if (crtr2.equals("completed")){
                int i = allTasks.size();
                for (int j = 0; j<i; j++){
                    if (allTasks.get(j).getStatus().equals("completed")){
                        filteredTasks.add(allTasks.get(j));
                    }
    
                }
            }else if (crtr2.equals("incomplete")){
                int i = allTasks.size();
                for (int j = 0; j<i; j++){
                    if (!allTasks.get(j).getStatus().equals("completed")){
                        filteredTasks.add(allTasks.get(j));
                    }
    
                }
            }else if (crtr2.equals("All")){
                filteredTasks=allTasks;
            }    
        }

        else if(crtr2.isEmpty()&& !crtr1.isEmpty()){
            if(crtr1.equals("ANYONE")){
                int i = allTasks.size();
                for (int j = 0; j<i; j++){
                    if (allTasks.get(j).getAssigned_to()==null){
                        filteredTasks.add(allTasks.get(j));
                    }
                }
            }
            else if (crtr1.equals("All")){
                filteredTasks=allTasks;
            }else{
                int i = allTasks.size();
                for (int j = 0; j<i; j++){
                    if (allTasks.get(j).getAssigned_to()!=null && allTasks.get(j).getAssigned_to().equals(crtr1)){
                        filteredTasks.add(allTasks.get(j));
                    }
                }
            }

        }

        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser=user.getUsername();
        model.addAttribute("tasks", filteredTasks);
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int k=notificsList.size();
        if (currentUser.startsWith("mngr")){
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("managers")){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }else{
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser) ){
                    usersNotifications.add(notificsList.get(h));
                }
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
        Set<Integer> ids=new HashSet<>();
        
        for (int y=0; y<usersMsgs.size();y++){
            Integer x=usersMsgs.get(y).getChat_id();
            
            ids.add(x);

            
        }
        for ( Integer i : ids){
            
            Set<messages> msgsWids=new HashSet<>();
            for (messages msg : usersMsgs){
                if (msg.getChat_id()==i){
                    msgsWids.add(msg);
                }
            }
            model.addAttribute("msgs"+i, msgsWids);
        }

        if (currentUser.startsWith("mngr")){
            return "ManagerOverview";
        }  
            
        return "ToDoList";
    }

    @PostMapping("/filter2")
    public String filterb(@RequestParam String crtr2, Model model, HttpServletRequest request) {
        staff user = (staff) request.getSession().getAttribute("staff");
        String currentUser = user.getUsername();
        List<tasks> allTasks=tskService.showTasks();
        List<tasks> usersTasks=new ArrayList<>();
        int i = allTasks.size();
        for (int j=0; j<i;  j++){
            if (allTasks.get(j).getAssigned_to()==null || allTasks.get(j).getAssigned_to().equals(currentUser)){
                usersTasks.add(allTasks.get(j));
            }
        }
        List<tasks> filteredTasks=new ArrayList<>();
        if (crtr2.equals("completed")){
            int k = usersTasks.size();
            for (int j = 0; j<k; j++){
                if (usersTasks.get(j).getStatus().equals("completed")){
                    filteredTasks.add(usersTasks.get(j));
                }

            }
        }else if (crtr2.equals("incomplete")){
            int k = usersTasks.size();
            for (int j = 0; j<k; j++){
                if (!usersTasks.get(j).getStatus().equals("completed")){
                    filteredTasks.add(usersTasks.get(j));
                }

            }
        }else if (crtr2.equals("All")){
            filteredTasks=usersTasks;
        }    

        model.addAttribute("tasks", filteredTasks);
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int k=notificsList.size();
        if (currentUser.startsWith("mngr")){
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("managers")){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }else{
            for (int h=0;h<k;h++){
                if(notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser) ){
                    usersNotifications.add(notificsList.get(h));
                }
            }

        }
        model.addAttribute("notifications", usersNotifications);

        if (currentUser.startsWith("mngr")){
            return "ManagerOverview";
        }  
            
        return "ToDoList";
    }
    
    
    @PostMapping("/clndr")
    public String clndrDateTask(@RequestParam(name="taskdate", required=true) String d, HttpServletRequest request, Model model) {
        Date date=java.sql.Date.valueOf(d);
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        String currentUser = user.getUsername();
        List<tasks> allTasks=tskService.showTasks();
        List<notifications> notificsList=ntfcService.showNotifics();
        List<notifications> usersNotifications=new ArrayList<>();
        int l=notificsList.size();
        if (currentUser.startsWith("mngr")){
            for (int h=0;h<l;h++){
                if(notificsList.get(h).getShow_to().equals("managers")){
                    usersNotifications.add(notificsList.get(h));
                }
            }
        }else{
            for (int h=0;h<l;h++){
                if(notificsList.get(h).getShow_to().equals("ANYONE")||notificsList.get(h).getShow_to().equals(currentUser) ){
                    usersNotifications.add(notificsList.get(h));
                }
            }

        }
        model.addAttribute("notifications", usersNotifications);

        if (currentUser.startsWith("mngr")){
            List<tasks> allUsersTasks=new ArrayList<>();
            int k= allTasks.size();
            for (int j=0; j<k; j++){
                if (allTasks.get(j).getDueDate().compareTo(date)==0){
                    allUsersTasks.add(allTasks.get(j));
                }
            }
            model.addAttribute("tasks", allUsersTasks);
            model.addAttribute("staff",stfService.showUsers());


            return "ManagerOverview";
        }else{
            List<tasks> usersTasks=new ArrayList<>();
            int i = allTasks.size();
            for (int j=0; j<i;  j++){
                if (allTasks.get(j).getAssigned_to()==null || allTasks.get(j).getAssigned_to().equals(currentUser)){
                    usersTasks.add(allTasks.get(j));
                }
    
            }
            List<tasks> usersNewTasks=new ArrayList<>();
            int k= usersTasks.size();
            for (int j=0; j<k; j++){
                if (usersTasks.get(j).getDueDate().compareTo(date)==0){
                    usersNewTasks.add(usersTasks.get(j));
                }
            }
            model.addAttribute("tasks", usersNewTasks);
            return "ToDoList";

        }

        

            

        
    }
    
    @GetMapping("SignUp.html")
    public String stf(HttpServletRequest request){
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user != null) {
            return "SignUp";
        }
        return"redirect: /api2/mngr";
        
    }
    
    
    
    
    
  
    


    @PostMapping("add_tasks")
    public String saveTasks( @RequestParam String title , @RequestParam String describtion, @RequestParam String assigned_to,@RequestParam String dueDate, HttpServletRequest request,  RedirectAttributes redirectAttributes) {
        try{
            Date completed_on=java.sql.Date.valueOf("1111-11-11");
            Date parsedDueDate = java.sql.Date.valueOf(dueDate);
            String status="open";
            staff user = (staff) request.getSession().getAttribute("staff");
            String currentUser=user.getUsername();
            tskService.saveTask(title, describtion, assigned_to, parsedDueDate, status, completed_on, false, currentUser);
            tskService.showTasks();
  
            notifications newNotific=ntfcService.newNotif("new task has been added " + title, assigned_to);
            ntfcRepository.save(newNotific);
        

        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error2", "Task could not be saved. Please try again. All fields must be filled");
        }
        return "redirect:/api2/mngr";

    }
    @PostMapping("post_tasks")
    public String postTasks(@RequestParam String title , @RequestParam String describtion,@RequestParam String dueDate, HttpServletRequest request, RedirectAttributes redirectAttributes) {

      
        try{
            Date completed_on=java.sql.Date.valueOf("1111-11-11");
            Date parsedDueDate = java.sql.Date.valueOf(dueDate);
            String status="open";
            String title1=" TO ANYONE   "+title;
            staff user = (staff) request.getSession().getAttribute("staff");
            String currentUser=user.getUsername();
            tskService.saveTask(title1, describtion, null, parsedDueDate, status, completed_on, false, currentUser);
            tskService.showTasks();
            
            notifications newNotific=ntfcService.newNotif("new task has been added " + title, "ANYONE");
            ntfcRepository.save(newNotific);

        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error2", "Task could not be saved. Please try again. All fields must be filled");
        }
        return "redirect:/api2/mngr";

    }

    
    @PostMapping("add_ownTasks")
    public String saveOwnTasks(HttpServletRequest request, @RequestParam String title , @RequestParam String describtion,@RequestParam String dueDate, RedirectAttributes redirectAttributes) {
        staff user = (staff) request.getSession().getAttribute("staff");

        try{
            Date parsedDueDate = java.sql.Date.valueOf(dueDate);
            Date completed_on=java.sql.Date.valueOf("1111-11-11");
            String assigned_to=user.getUsername();
            String status="open";
            tskService.saveTask(title, describtion, assigned_to, parsedDueDate, status, completed_on, true, assigned_to);
            tskService.showTasks();
            

        }


        catch(Exception e){
            redirectAttributes.addFlashAttribute("error2", "Task could not be saved. Please try again. All fields must be filled");

            
        }
        if (user.getUsername().startsWith("mngr")){
            return "redirect:/api2/mngr";
        }
        return "redirect:/api1/tasks";

        
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

}