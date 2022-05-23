package com.actia.projects.dto;

import java.time.LocalDateTime;
public class RequestFileDto {

	private String id;
	private String name;
	public LocalDateTime  echeanceRC;
	public LocalDateTime  echeanceRD;
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
	
	public LocalDateTime getEcheanceRC() {
		return echeanceRC;
	}
	public void setEcheanceRC(LocalDateTime echeanceRC) {
		this.echeanceRC = echeanceRC;
	}
	public LocalDateTime getEcheanceRD() {
		return echeanceRD;
	}
	public void setEcheanceRD(LocalDateTime echeanceRD) {
		this.echeanceRD = echeanceRD;
	}
	public RequestFileDto() {
		super();
	}
    
	
}
