package com.actia.projects.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.actia.projects.entities.RequestFile.Cible;
import com.actia.projects.entities.RequestFile.Fonctionnalite;

@Entity
@Table(name = "logs")
public class Log {
	
	@Id
	@GeneratedValue(generator= "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	private int numero;
	private String infos;
	private String description;
	private String filename;
	private String phrase;
	private Date date;
	
	@Enumerated(EnumType.STRING)
	private LogType type;
	public enum LogType{
		Error, TBBT
		}

	private Fonctionnalite fonctionnalite;
	private Cible cible;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
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
	public Cible getCible() {
		return cible;
	}
	public void setCible(Cible cible) {
		this.cible = cible;
	}
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Log(String id, int numero, String infos, String description, String filename, String phrase, Date date,
			LogType type, Fonctionnalite fonctionnalite, Cible cible) {
		super();
		this.id = id;
		this.numero = numero;
		this.infos = infos;
		this.description = description;
		this.filename = filename;
		this.phrase = phrase;
		this.date = date;
		this.type = type;
		this.fonctionnalite = fonctionnalite;
		this.cible = cible;
	}
	
	




}