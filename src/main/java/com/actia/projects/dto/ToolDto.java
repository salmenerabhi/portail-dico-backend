package com.actia.projects.dto;

public class ToolDto {

	
	private Integer id;
    private String  toolId;
    private String nom;
    private String type;
    

	private UserDto user;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getToolId() {
		return toolId;
	}


	public void setToolId(String toolId) {
		this.toolId = toolId;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public UserDto getUser() {
		return user;
	}


	public void setUser(UserDto user) {
		this.user = user;
	}


	public ToolDto(Integer id, String toolId, String nom, String type, UserDto user) {
		super();
		this.id = id;
		this.toolId = toolId;
		this.nom = nom;
		this.type = type;
		this.user = user;
	}


	public ToolDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
