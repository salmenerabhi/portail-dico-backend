package com.actia.projects.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	
	@Column(name = "title", length = 1024)

	private String title;
    private String question;
    private String answer;
    
    @ManyToOne
    private UserEntity user;
    
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	public Faq(String id, String title, String question, String answer, UserEntity user) {
		super();
		this.id = id;
		this.title = title;
		this.question = question;
		this.answer = answer;
		this.user = user;
	}
	public Faq() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
