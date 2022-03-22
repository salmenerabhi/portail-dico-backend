package com.actia.projects.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.actia.projects.entities.Faq;
import com.actia.projects.entities.Notification;
import com.actia.projects.repository.NotificationRepository;

@Service
public class NotificationService {

	
	@Autowired
    NotificationRepository notificationRepository;
	
	
	public Notification addNotification(Notification notification) {
		Notification alert= notificationRepository.save(notification);
		return alert;
		
	}

	public List<Notification> getAllNotifs() {
		return (List<Notification>) notificationRepository.findAll();
	}

}
