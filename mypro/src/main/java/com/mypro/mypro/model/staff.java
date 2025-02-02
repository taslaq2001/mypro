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

public class staff {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
	private String name;
	private String username;
	private String password;
	private String email;
}