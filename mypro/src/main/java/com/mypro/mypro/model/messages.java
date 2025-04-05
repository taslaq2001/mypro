package com.mypro.mypro.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int message_id ;
    private Integer chat_id ; 
    private String sender ; 
    private String receiver ;
    private String message ;  
    private LocalDateTime created_at ;

    public void setChat_id(int chat_id){
        this.chat_id=chat_id;
    }

    public void setSender(String sender){
        this.sender=sender;
    }

    public void setReceiverer(String receiver){
        this.receiver=receiver;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public void setCreated_at(LocalDateTime created_at){
        this.created_at=created_at;
    }

    public Integer getChat_id(){
        return chat_id;
    }

    public String getSender(){
        return sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public String getMessage(){
        return message;
    }

    public LocalDateTime getCreated_at(){
        return created_at;
    }
}
