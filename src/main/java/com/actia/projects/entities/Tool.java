package com.actia.projects.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Tool {

	 @Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;
	  private String name;
	  private String description;
	  private String type;

	  @Lob
	  private byte[] data; 
	  
	  @OneToOne( optional = true, cascade = CascadeType.ALL)
	    private FileDB image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public FileDB getImage() {
		return image;
	}

	public void setImage(FileDB image) {
		this.image = image;
	}

	public Tool( String name, String description, String type, byte[] data, FileDB image) {
		super();
		
		this.name = name;
		this.description = description;
		this.type = type;
		this.data = data;
		this.image = image;
	}

	public Tool() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	  
	  
	  
	  
}
