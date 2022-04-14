package com.actia.projects.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Brand {
	@Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	  private String marque;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Brand(String id, String marque) {
		super();
		this.id = id;
		this.marque = marque;
	}
	  
	  
}
