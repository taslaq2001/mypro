package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mypro.mypro.repository.chatsRepository;
import com.mypro.mypro.repository.messagesRepository;
import com.mypro.mypro.repository.notificationsRepository;
import com.mypro.mypro.repository.tasksRepository;
import com.mypro.mypro.service.chatsService;
import com.mypro.mypro.service.messagesService;
import com.mypro.mypro.service.notificationsService;
import com.mypro.mypro.service.staffService;
import com.mypro.mypro.service.tasksService;
import jakarta.servlet.http.HttpServletRequest;
import com.mypro.mypro.model.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api1")

public class tasksController{
    @Autowired tasksService tskService;
    @Autowired tasksRepository tskrepository;
    @Autowired notificationsService ntfcService;
    @Autowired notificationsRepository ntfcRepository;
    @Autowired chatsRepository chtRepository;
    @Autowired chatsService chtService;
    @Autowired messagesRepository msgRepository;
    @Autowired messagesService mesgService;
    @Autowired staffService stfService;
    @Autowired WebController wbcntrlr;



    @GetMapping("/tasks")
    public String mainPage(HttpServletRequest request, Model model) {

        if (!wbcntrlr.validLogin(request)) {return "Welcome";}
        String currentUser=wbcntrlr.getCurrentUser(request);

        model.addAttribute("chats", chtService.showUsersChats(request));
        model.addAttribute("messages", mesgService.showUsersMessages(request));
        model.addAttribute("notifications", ntfcService.showUsersNotifics(request));
        model.addAttribute("tasks",tskService.showUsersTasks(request));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("staff",stfService.showUsers());
                    
        return "ToDoList";
    }   




    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id ,HttpServletRequest request) {

        Optional<tasks> taskOptional = tskrepository.findById(id);
        if (taskOptional.isPresent()) {
            tasks taskA = taskOptional.get();
            tskService.delete(taskA);
            notifications newNotific=ntfcService.newNotif("one task has been deleted " + taskA.getTitle(), taskA.getAssigned_to());
            ntfcRepository.save(newNotific);
        }
  
        return wbcntrlr.mngrOrUser(request); 
    }

    @DeleteMapping("/delete1/{id}")
    public String deleteChat(@PathVariable("id") int id ,HttpServletRequest request) {

        Optional<chats> cht = chtRepository.findById(id);
        if (cht.isPresent()) {
            chats chtA = cht.get();
            String currentUser=wbcntrlr.getCurrentUser(request);    
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
            
        }

        return wbcntrlr.mngrOrUser(request);
            
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
        
        return wbcntrlr.mngrOrUser(request);

    }

    @PostMapping("/filter")
    public String filter(@RequestParam(name="crtr1", required = false) String crtr1,@RequestParam(name="crtr2", required = false) String crtr2, Model model, HttpServletRequest request) {
        model.addAttribute("staff",stfService.showUsers());
        String currentUser=wbcntrlr.getCurrentUser(request);
        List<tasks> allTasks=tskService.showUsersTasks(request);
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
            return filterb(crtr2, model, request);
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
        model.addAttribute("chats", chtService.showUsersChats(request));
        model.addAttribute("messages", mesgService.showUsersMessages(request));
        model.addAttribute("notifications", ntfcService.showUsersNotifics(request));
        model.addAttribute("tasks", filteredTasks);
        model.addAttribute("currentUser", currentUser);
        return "ManagerOverview";        
    }

    @PostMapping("/filter2")
    public String filterb(@RequestParam String crtr2, Model model, HttpServletRequest request) {
        String currentUser=wbcntrlr.getCurrentUser(request);
        List<tasks> usersTasks=tskService.showUsersTasks(request);
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
        model.addAttribute("chats", chtService.showUsersChats(request));
        model.addAttribute("messages", mesgService.showUsersMessages(request));
        model.addAttribute("notifications", ntfcService.showUsersNotifics(request));
        model.addAttribute("tasks", filteredTasks); 
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("staff",stfService.showUsers()); 
        if (currentUser.startsWith("mngr")){
            return "ManagerOverview";
        }else{
            return "ToDoList";
        }         
        
    }

    @GetMapping("/filter")
    public String backToMaiinPage(HttpServletRequest request) {
        return wbcntrlr.mngrOrUser(request);
    }

    @GetMapping("/filter2")
    public String backToMaiinPage2(HttpServletRequest request) {
        return wbcntrlr.mngrOrUser(request);
    }
    
    
    
    @PostMapping("/clndr")
    public String clndrDateTask(@RequestParam(name="taskdate", required=true) String d, HttpServletRequest request, Model model) {
        Date date=java.sql.Date.valueOf(d);

        if (!wbcntrlr.validLogin(request)) {return "Welcome";}

        String currentUser=wbcntrlr.getCurrentUser(request);

        model.addAttribute("chats", chtService.showUsersChats(request));
        model.addAttribute("messages", mesgService.showUsersMessages(request));
        model.addAttribute("notifications", ntfcService.showUsersNotifics(request));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("staff",stfService.showUsers());

        List<tasks> allTasks=tskService.showUsersTasks(request);

        if (currentUser.startsWith("mngr")){   
            List<tasks> filteredTasks=new ArrayList<>();
            int k= allTasks.size();
            for (int j=0; j<k; j++){
                if (allTasks.get(j).getDueDate().compareTo(date)==0){
                    filteredTasks.add(allTasks.get(j));
                }
            }

            model.addAttribute("tasks", filteredTasks); 
            return "ManagerOverview";   
        }else{
            List<tasks> filteredTasks=new ArrayList<>();
            int k= allTasks.size();
            for (int j=0; j<k; j++){
                if (allTasks.get(j).getDueDate().compareTo(date)==0){
                    filteredTasks.add(allTasks.get(j));
                }
            }
            
            model.addAttribute("tasks", filteredTasks);
            return "ToDoList";

        }
        
    }

    @GetMapping("/clndr")
    public String backToMaiinPage3(HttpServletRequest request) {
        return wbcntrlr.mngrOrUser(request);
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
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error2", "Task could not be saved. Please try again. All fields must be filled");    
        }
        return wbcntrlr.mngrOrUser(request);         
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
        return ("Welcome");
    }

}