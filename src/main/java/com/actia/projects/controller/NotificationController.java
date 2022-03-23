package com.actia.projects.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.actia.projects.entities.Notification;
import com.actia.projects.entities.UserEntity;
import com.actia.projects.repository.UserRepository;
import com.actia.projects.services.NotificationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

	
	@Autowired
	NotificationService notificationService;
	@Autowired
	UserRepository userRepository ;
	
	
	/*
	@PostMapping("/addNotif")
	@ResponseBody
	public Notification ajouterNotif(@RequestBody Notification notification){
		UserEntity responsible=userRepository.findById(notification.getResponsible().getId()).get()	;
		notification.setResponsible(responsible);
	Notification notif  = notificationService.addNotification(notification);
		return notif;
	}
	
	@GetMapping()
	@ResponseBody
	public List<Notification> getListNotifs(){
		return notificationService.getAllNotifs();
	}
	*/
}
