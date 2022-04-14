package com.actia.projects.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Target {

	@Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	  private String cible;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCible() {
		return cible;
	}
	public void setCible(String cible) {
		this.cible = cible;
	}
	public Target() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Target(String id, String cible) {
		super();
		this.id = id;
		this.cible = cible;
	}
	  
	  
}
