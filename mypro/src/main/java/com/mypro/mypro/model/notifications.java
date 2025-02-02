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
public class notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notification_id;
    private int task_id;
    private int show_to;
    private String status;
    private LocalDateTime created_at;
}