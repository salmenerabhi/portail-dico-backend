package com.actia.projects.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity

public class Notification {
	
	@Id
	@GeneratedValue(generator= "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable = true)
	private Date delaimax;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Type type;
	public enum Type{
		Critical,Reminder,Informational
	}
	
	 @ManyToOne(cascade = CascadeType.ALL)

	    private UserEntity responsible;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDelaimax() {
		return delaimax;
	}

	public void setDelaimax(Date delaimax) {
		this.delaimax = delaimax;
	}

	

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public UserEntity getResponsible() {
		return responsible;
	}

	public void setResponsible(UserEntity responsible) {
		this.responsible = responsible;
	}

	

	public Notification(String id, Date delaimax, Type type, UserEntity responsible) {
		super();
		this.id = id;
		this.delaimax = delaimax;
		this.type = type;
		this.responsible = responsible;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 


}
