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
    private Integer chat_id;
	private String first_person;
	private String second_person;
    private String deleted_by;
    private String dltText;

    public void setFirst_person( String first_person){
        this.first_person=first_person;
    }

    public void setSecond_person(String second_person){
        this.second_person=second_person;
    }

    public String getFirst_person(){
        return first_person;
    }

    public String getSecond_person(){
        return second_person;
    }

    public Integer getChat_id(){
        return chat_id;
    }
    public void setDeleted_by(String deleted_by){
        this.deleted_by=deleted_by;
    }
    public String getDeleted_by(){
        return deleted_by;
    }

    public String getDltText(){
        return dltText;
    }

    public void setDltText(String dltText){
        this.dltText=dltText;
    }
}