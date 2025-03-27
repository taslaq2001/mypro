package com.mypro.mypro.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class tasks{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int task_id;
	private String title ;
	private String describtion;
	private String assigned_to;
	private Date dueDate;
    private String status;
    private Date created_at;
	private Date completed_on;
    private String assigned_by;
    private Boolean privacy = false;

    public void setTitle(String title){
        this.title=title;
    }
    public void setDescribtion(String describtion){
        this.describtion=describtion;
    }
    public void setAssigned_to(String assigned_to){
        this.assigned_to=assigned_to;
    }
    public void setDueDtae(Date dueDate){
        this.dueDate=dueDate;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setCreated_at(Date created_at){
        this.created_at=created_at;
    }
    public void setCompleted_on(Date completed_on){
        this.completed_on=completed_on;
    }
    public void setPrivacy(Boolean privacy){
        this.privacy=privacy;
    }
    public void setAssigned_by(String assigned_by){
        this.assigned_by=assigned_by;
    }
    public String getTitle(){
        return title;
    }
    public String getDescribtion(){
        return describtion;
    }
    public String getAssigned_to(){
        return assigned_to;
    }
    public Date getDueDate(){
        return dueDate;
    }
    public String getStatus(){
        return status;
    }
    public Date getCreated_at(){
        return created_at;
    }
    public Date getCompleted_on(){
        return completed_on;
    }
    public Boolean getPrivacy(){
        return privacy;
    }
    public String getAssigned_by(){
        return assigned_by;
    }


}

