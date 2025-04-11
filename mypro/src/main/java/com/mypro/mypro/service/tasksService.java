package com.mypro.mypro.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mypro.mypro.Controller.WebController;
import com.mypro.mypro.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mypro.mypro.repository.tasksRepository;
import jakarta.servlet.http.HttpServletRequest;


@Service

public class tasksService{

    @Autowired tasksRepository tskrepository;
    @Autowired WebController wbcntrlr;


    public tasks saveTask(String title , String describtion, String assigned_to, Date dueDate, String status, Date completed_on, Boolean privacy, String assigned_by){
        tasks task=new tasks();
        Date d=new Date(System.currentTimeMillis());
        task.setTitle(title);
        task.setDescribtion(describtion);
        task.setAssigned_to(assigned_to);
        task.setDueDate(dueDate);
        task.setStatus(status);
        task.setCreated_at(d);
        task.setCompleted_on(completed_on);
        task.setAssigned_by(assigned_by);
        task.setPrivacy(privacy);
        return tskrepository.save(task);
    }

    public List<tasks> showTasks(){
                
        List<tasks> allTasks = tskrepository.findAll();
        return allTasks;
    
    }

    public void delete(tasks task){
        tskrepository.delete(task);
    }

    public List<tasks> showUsersTasks(HttpServletRequest request){
        String currentUser=wbcntrlr.getCurrentUser(request);

        List<tasks> allTasks=showTasks();
        int f=allTasks.size();
        if (currentUser.startsWith("mngr")){
            Iterator<tasks> iterator = allTasks.iterator();
            while (iterator.hasNext()) {
                tasks task = iterator.next();
                if (task.getPrivacy() != null && task.getPrivacy()) {
                    if (!currentUser.equals(task.getAssigned_to())) {
                        iterator.remove(); 
                    }
                }
            }
            return allTasks;
        }else{
            List<tasks> usersTasks=new ArrayList<>();
            for (int j=0; j<f;  j++){
                if (allTasks.get(j).getAssigned_to()==null || allTasks.get(j).getAssigned_to().equals(currentUser)){
                    usersTasks.add(allTasks.get(j));
                }
            }
            return usersTasks;

        }


    }
}