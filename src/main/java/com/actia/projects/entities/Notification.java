package com.actia.projects.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	private String type;
	


}
