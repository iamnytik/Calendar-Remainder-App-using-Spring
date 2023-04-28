package com.example.services;

import com.example.model.EmailDetails;

public interface EmailService {

	String sendMail(EmailDetails details);
	
}
