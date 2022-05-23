package com.actia.projects.dto;

public class StatRequestFiles {

	private int rejected;
	private int unstarted;
	private int finished;
	private int in_progress;
	private int verified;
	private int to_verify;
	private int nbrdemandes;
	private int nbrapproximation;
	private int nbrdemandestraites;
	private int nbrdemandesrejetes;
	private int nbrapproximationtraites;
	private int nbrapproximationrejetes;


	public int getNbrdemandes() {
		return nbrdemandes;
	}
	public void setNbrdemandes(int nbrdemandes) {
		this.nbrdemandes = nbrdemandes;
	}
	public int getNbrapproximation() {
		return nbrapproximation;
	}
	public void setNbrapproximation(int nbrapproximation) {
		this.nbrapproximation = nbrapproximation;
	}
	public int getNbrdemandestraites() {
		return nbrdemandestraites;
	}
	public void setNbrdemandestraites(int nbrdemandestraites) {
		this.nbrdemandestraites = nbrdemandestraites;
	}
	public int getNbrdemandesrejetes() {
		return nbrdemandesrejetes;
	}
	public void setNbrdemandesrejetes(int nbrdemandesrejetes) {
		this.nbrdemandesrejetes = nbrdemandesrejetes;
	}
	public int getNbrapproximationtraites() {
		return nbrapproximationtraites;
	}
	public void setNbrapproximationtraites(int nbrapproximationtraites) {
		this.nbrapproximationtraites = nbrapproximationtraites;
	}
	public int getNbrapproximationrejetes() {
		return nbrapproximationrejetes;
	}
	public void setNbrapproximationrejetes(int nbrapproximationrejetes) {
		this.nbrapproximationrejetes = nbrapproximationrejetes;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public int getUnstarted() {
		return unstarted;
	}
	public void setUnstarted(int unstarted) {
		this.unstarted = unstarted;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	public int getIn_progress() {
		return in_progress;
	}
	public void setIn_progress(int in_progress) {
		this.in_progress = in_progress;
	}
	public StatRequestFiles() {
		super();
	}
	public int getVerified() {
		return verified;
	}
	public void setVerified(int verified) {
		this.verified = verified;
	}
	public int getTo_verify() {
		return to_verify;
	}
	public void setTo_verify(int to_verify) {
		this.to_verify = to_verify;
	}
	public StatRequestFiles(int rejected, int unstarted, int finished, int in_progress) {
		super();
		this.rejected = rejected;
		this.unstarted = unstarted;
		this.finished = finished;
		this.in_progress = in_progress;
	}
	
	
}
