package com.actia.projects.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.actia.projects.controller.NotificationController;
import com.actia.projects.dto.ResetPassword;
import com.actia.projects.dto.UserDto;

@Service
public class PasswordServiceImpl implements PasswordService{
	
	@Autowired
	UserService userService;
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	NotificationController notificationController;
	
	//Reset password
	@Override
	public void resetPassword(ResetPassword resetPassword,String email) {
		UserDto user= userService.getUserByEmail(email);
		user.setPassword(resetPassword.getPassword());
		 userService.updateUser(user);

		
	}

	//Send recovery password mail
	@Override
	public void sendEmail(String email) throws MessagingException {
		 UserDto user = userService.getUserByEmail(email);
		 if(user!=null) {
		  MimeMessage message = emailSender.createMimeMessage();
	      boolean multipart = true;
	      MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
	      
	      String htmlMsg = "<h2>Forgot password</h3>"
	   		   +"<h3>Password:</h2><url>"+"http://localhost:4200/resetPassword/"+ jwtUtil.generateTokenByEmail(email)+"</url>";
	 
	      message.setContent(htmlMsg, "text/html");
	      helper.setTo(email);
	      helper.setSubject("Password");
	      this.emailSender.send(message);
		 }
	}
	
	

}
