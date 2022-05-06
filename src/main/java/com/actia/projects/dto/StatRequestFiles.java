package com.actia.projects.dto;

public class StatRequestFiles {

	private int rejected;
	private int unstarted;
	private int finished;
	private int in_progress;
	private int verified;
	private int to_verify;

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
		// TODO Auto-generated constructor stub
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
