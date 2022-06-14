package com.actia.projects.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actia.projects.entities.Notifications;

@RestController

public class NotificationController {
	@Autowired
    private SimpMessagingTemplate template;

	
    @GetMapping("/notify")
    public String getNotification(Notifications notifications ) {
    
        // Push notifications to front-end
        template.convertAndSend("/topic/notification", notifications);

        return "Notifications successfully sent to Angular !";
    }
    
	
}