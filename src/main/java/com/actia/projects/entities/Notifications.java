package com.actia.projects.entities;

public class Notifications {

    private int count;
    private String message;

   
    public Notifications(int count, String message) {
		super();
		this.count = count;
		this.message = message;
	}
	public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void increment() {
        this.count++;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
