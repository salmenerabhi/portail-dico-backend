package com.actia.projects.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")

public class UserEntity {
	@Id
	@GeneratedValue(generator= "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	@Column(nullable = true, length = 50)
	private String firstName;
	@Column(nullable = true, length = 50)
	private String lastName;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String email;
	@Column(nullable = true)
	private Date creationDate;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(name = "site")
	@Enumerated(EnumType.STRING)
	private Site site;
	@OneToOne( optional = true, cascade = CascadeType.ALL)
    private FileDB image;
	
	@OneToMany( cascade = CascadeType.ALL)
	  private Set<Tool> tool;
	@OneToMany( cascade = CascadeType.ALL)
	  private List<RequestFile> requestFile;

	public Set<Tool> getTool() {
		return tool;
	}

	public void setTool(Set<Tool> tool) {
		this.tool = tool;
	}

	
	public List<RequestFile> getRequestFile() {
		return requestFile;
	}

	public void setRequestFile(List<RequestFile> requestFile) {
		 this.requestFile = requestFile;
	}
	
	

	public UserEntity() {
		super();

	}

	public UserEntity(String password, String email) {
        this.password = password;
        this.email = email;
    }




	public UserEntity(String id, String firstName, String lastName, String password, String email, Date creationDate,
			Role role, Site site, FileDB image, Set<Tool> tool) {
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
		this.tool = tool;
	}

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
	
	

}
