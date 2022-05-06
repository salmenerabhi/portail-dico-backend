package com.actia.projects.services;

import javax.mail.MessagingException;

import com.actia.projects.dto.ResetPassword;

public interface PasswordService {

	void resetPassword(ResetPassword resetPassword,String email);
	void sendEmail(String email)throws MessagingException;
}
