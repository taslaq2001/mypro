package com.mypro.mypro.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;


import com.mypro.mypro.repository.staffRepository;
import com.mypro.mypro.repository.tasksRepository;
import com.mypro.mypro.service.staffService;
import com.mypro.mypro.service.tasksService;

import jakarta.servlet.http.HttpServletRequest;
import com.mypro.mypro.model.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /* 
    @GetMapping("/tasks")
    public String tsks(){
        return "ToDoList";
    }
    */
    @GetMapping("/tasks")
    public String mainPage(HttpServletRequest request, Model model) {
        staff user = (staff) request.getSession().getAttribute("staff");
        if (user == null) {
            return "redirect:/api1/Welcome.html";
        }
        int currentUser = user.getPerson_id();
        List<tasks> allTasks=tskService.showTasks();
        List<tasks> usersTasks=new ArrayList<>();
        int i = allTasks.size();
        for (int j=0; j<i;  j++){
            if (allTasks.get(j).getAssigned_to()==currentUser){
                usersTasks.add(allTasks.get(j));
            }

        }
        model.addAttribute("tasks", usersTasks);
        return "ToDoList";
    }   


    // Make sure to autowire the service properly


    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Optional<tasks> taskOptional = tskrepository.findById(id);
        if (taskOptional.isPresent()) {
            tasks taskA = taskOptional.get();
            tskService.delete(taskA);
            redirectAttributes.addFlashAttribute("message", "Task deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Task not found.");
        }
        return "redirect:/api1/tasks";
    }
    @PostMapping("change1/{id}")
    public String taskCompleted(@PathVariable("id") int id,@RequestParam("status") String status, @RequestParam(value = "otherStatus", required = false) String otherStatus) {
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

        }    
        return "redirect:/api1/tasks";

    }
    


    
    
    
    
    
  
    
    /* 
    @Autowired
    private staffService stfservice;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, Model model) throws NullPointerException {
        try {
            stfservice.loginStaff(username, password);
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
    private staffService stfservice;
    @PostMapping("/login")
    public String logIn(@RequestParam String username,@RequestParam String password, HttpServletRequest request) {
        try {
            staff user = stfservice.validateLogin(username, password, request);
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


    @PostMapping("add_tasks")
    public String saveTasks(@RequestParam String title , @RequestParam String describtion, @RequestParam int assigned_to,@RequestParam Date dueDate, @RequestParam String status) {
        Date completed_on=java.sql.Date.valueOf("1111-11-11");
        tskService.saveTask(title, describtion, assigned_to, dueDate, status, completed_on);
        tskService.showTasks();
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