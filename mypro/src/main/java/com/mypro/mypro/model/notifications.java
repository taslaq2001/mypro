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
public class notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notification_id;
    private boolean seen;
    private String content;
    private String show_to;
    private Date created_at;

    public void setSeen(boolean seen){
        this.seen=seen;
    }

    public void setContent( String content){
        this.content=content;
    }

    public void setShow_to(String show_to){
        this.show_to=show_to;
    }

    public void setCreated_at( Date created_at){
        this.created_at=created_at;
    }

    public boolean getSeen(){
        return seen;
    }

    public String getContent(){
        return content;
    }

    public String getShow_to(){
        return show_to;
    }

    public Date getCreated_at(){
        return created_at;
    }
}

