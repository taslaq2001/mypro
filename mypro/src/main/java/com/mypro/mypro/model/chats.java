package com.mypro.mypro.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class chats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chat_id;
	private int person1;
	private int person2;
}