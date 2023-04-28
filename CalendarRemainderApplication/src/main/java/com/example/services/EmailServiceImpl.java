package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.model.EmailDetails;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.MessagingException;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;

@Service
public class EmailServiceImpl { //implements EmailService{

	String from = "pathignanchandan@gmail.com";
	String host = "smtp.gmail.com";
	
	//@Autowired
	//private JavaMailSender javaMailSender;
	
	JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	
	
	@Autowired
	private MailProperties mailProperties;
	
	public void sendMail(EmailDetails details) throws MessagingException {
		
		jakarta.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
		messageHelper.setFrom(mailProperties.getUsername());
		messageHelper.setTo(details.getRecipient());
		messageHelper.setSubject(details.getSubject());
		messageHelper.setText(details.getMsgBody());
		javaMailSender.send(mimeMessage);
		
		System.out.println("mail sent successfully");
		
		
		
	}	
}
