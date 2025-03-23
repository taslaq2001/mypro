package com.mypro.mypro.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mypro.mypro.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import com.mypro.mypro.repository.tasksRepository;

@Service

public class tasksService{
    @Autowired
    private tasksRepository tskrepository;
    public tasks saveTask(String title , String describtion, String assigned_to, Date dueDate, String status, Date completed_on){
        tasks task=new tasks();
        Date d=new Date(System.currentTimeMillis());
        task.setTitle(title);
        task.setDescribtion(describtion);
        task.setAssigned_to(assigned_to);
        task.setDueDate(dueDate);
        task.setStatus(status);
        task.setCreated_at(d);
        task.setCompleted_on(completed_on);
        return tskrepository.save(task);
    }
    public List<tasks> showTasks(){
                //
        List<tasks> allTasks = tskrepository.findAll();
        return allTasks;
    
    }
    public void delete(tasks task){
        tskrepository.delete(task);
    }
}