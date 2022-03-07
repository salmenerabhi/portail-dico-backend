package com.actia.projects.dto;

import java.util.Date;

import com.actia.projects.entities.FileDB;
import com.actia.projects.entities.Role;
import com.actia.projects.entities.Site;

public class UserDto {

	
	private String id;
    private String firstName;
    private String lastName;
    
    private String password;
    private String email;
    private Date creationDate;
    private Role role;
    private Site site;
    private FileDB image;

    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	
	public FileDB getImage() {
		return image;
	}
	public void setImage(FileDB image) {
		this.image = image;
	}
	public UserDto(String id, String firstName, String lastName, String password, String email, Date creationDate,
			Role role, Site site, FileDB image) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.creationDate = creationDate;
		this.role = role;
		this.site = site;
		this.image = image;
	}
	public UserDto() {
		super();
	}
    
    
    
}
