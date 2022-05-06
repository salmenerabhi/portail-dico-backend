package com.actia.projects.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity

public class RequestFile  implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
	private String ecu;
	public Date echeanceRC;
	public Date echeanceRD;
	private String commentaire;
	private long nombrephrase;
	
	
	@Column(name ="fileType")
	@Enumerated(EnumType.STRING)
	private FileType fileType;
	public enum FileType{
		Approximation, Demande
	}
	
	@Column(name ="fonctionnalite")
	@Enumerated(EnumType.STRING)
	private Fonctionnalite fonctionnalite;
	public enum Fonctionnalite{
		DEFAULT,MPM,MENU,FNC,TA
	}
	@Column(name ="langue")
	@Enumerated(EnumType.STRING)
	private Langue langue;
	public enum Langue{
		FR,EN,DE
	}
	@Column(name ="state")
	@Enumerated(EnumType.STRING)
	private State state;
	public enum State{
		unstarted,in_progress,rejected,finished,to_verify,verified
	}	
	
	@ManyToOne
	 private UserEntity user;

	@OneToMany (cascade=CascadeType.ALL) 
	private List<Checklist> checklist;

	@ManyToOne()  
	 private Brand marque;
	
	@ManyToOne()  
	private Target cible;
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<Checklist> getChecklist() {
		return checklist;
	}
	public void setChecklist(List<Checklist> checklist) {
		this.checklist = checklist;
	}
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
	
	public String getEcu() {
		return ecu;
	}
	public void setEcu(String ecu) {
		this.ecu = ecu;
	}
	public Date getEcheanceRC() {
		return echeanceRC;
	}
	public void setEcheanceRC(Date echeanceRC) {
		this.echeanceRC = echeanceRC;
	}
	public Date getEcheanceRD() {
		return echeanceRD;
	}
	public void setEcheanceRD(Date echeanceRD) {
		this.echeanceRD = echeanceRD;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	
	
	public long getNombrephrase() {
		return nombrephrase;
	}
	public void setNombrephrase(long nombrephrase) {
		this.nombrephrase = nombrephrase;
	}
	public Fonctionnalite getFonctionnalite() {
		return fonctionnalite;
	}
	public Brand getMarque() {
		return marque;
	}
	public void setMarque(Brand marque) {
		this.marque = marque;
	}
	public Target getCible() {
		return cible;
	}
	public void setCible(Target cible) {
		this.cible = cible;
	}
	public void setFonctionnalite(Fonctionnalite fonctionnalite) {
		this.fonctionnalite = fonctionnalite;
	}
	public Langue getLangue() {
		return langue;
	}
	public void setLangue(Langue langue) {
		this.langue = langue;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}


	
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public RequestFile() {
		super();
		// TODO Auto-generated constructor stub
	}  
	  
	
	
}
