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
    private int chat_id ; 
    private int sender_id ; 
    private int receiver_id ;
    private String message ;  
    private LocalDateTime created_at ;
}
