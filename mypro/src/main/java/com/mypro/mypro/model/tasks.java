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
public class tasks{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int task_id;
	private String title ;
	private String describtion;
	private int assigned_to;
	private LocalDateTime dueDate;
    private String status;
    private LocalDateTime creeated_at;
	private LocalDateTime completed_on;
}
