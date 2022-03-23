package com.actia.projects.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity

public class Checklist {

	@Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	  private String infos;
	  private Boolean state;
	  
	 
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}

	public Checklist(String id, String infos, Boolean state) {
		super();
		this.id = id;
		this.infos = infos;
		this.state = state;
	}
	public Checklist() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	  


}
