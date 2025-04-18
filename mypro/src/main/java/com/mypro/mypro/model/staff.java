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

	public void setName (String name){
		this.name=name;
	}
	public String getNme (){
		return name;
	}
	public void setUsername (String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword( String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setEmail( String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}
}


