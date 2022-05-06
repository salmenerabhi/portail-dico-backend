package com.actia.projects.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Target {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(unique = true)
	private String cible;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cible")
	private List<RequestFile> requestfile;

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

	public Target(String cible) {
		super();
		this.cible = cible;
	}

}
