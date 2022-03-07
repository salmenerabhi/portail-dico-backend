package com.actia.projects.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.engine.internal.Cascade;

@Entity
public class Faq {
	
	@Id
	@GeneratedValue(generator= "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;

    private String question;
    private String answer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity user;
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Faq(String id, String question, String answer) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
	}
	public Faq() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
