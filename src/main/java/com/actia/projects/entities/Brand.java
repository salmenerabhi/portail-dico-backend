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
public class Brand {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(unique=true)
	private String marque;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "marque")
	private List<RequestFile> requestfile;

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

	public Brand(String marque) {
		super();
		this.marque = marque;
	}

}
