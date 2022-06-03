package com.actia.projects.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.actia.projects.entities.RequestFile.Fonctionnalite;

@Entity
@Table(name = "logs")
public class Log {
	
	@Id
	@GeneratedValue(generator= "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	private String numero;
	private String infos;
	private String description;
	private String filename;
	private String phrase;
	private Date date;
	private Date creationDate;
	
	@Enumerated(EnumType.STRING)
	private LogType type;
	public enum LogType{
		Error, TBBT_FNC,TBBT_GPC,TBBT_MENU, Ref, TBBT_FNC_Ref,TBBT_GPC_Ref,TBBT_MENU_Ref
		}

	private Fonctionnalite fonctionnalite;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getInfos() {
		return infos;
	}
	public void setInfos(String infos) {
		this.infos = infos;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public LogType getType() {
		return type;
	}
	public void setType(LogType type) {
		this.type = type;
	}
	public Fonctionnalite getFonctionnalite() {
		return fonctionnalite;
	}
	public void setFonctionnalite(Fonctionnalite fonctionnalite) {
		this.fonctionnalite = fonctionnalite;
	}
	

	public Log() {
		super();
	}
	public Log(String numero2, String description2, int infos2) {
	}
	
	




}
