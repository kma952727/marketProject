package com.example.demo.config.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SimpleEmailServiceImpl {
	
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private MailKeyGenerator mailKeyGenerator;
	private String authKey;
	
	public String sendMail(String to) throws MessagingException {
		authKey = mailKeyGenerator.getKey(50, false);
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject("이메일 인증 메일");
		message.setRecipient(Message.RecipientType.TO, 
				new InternetAddress(to));
		message.setText("<h1>링크를 클릭해서 인증해주세요.</h1>" +
				"<a herf='http://localhost:8080/emailVerified?username=tmpUser&authKey='"+authKey+"</a>");
		message.setFrom("FreeMarket");
		message.setSentDate(new Date());
		javaMailSender.send(message);
		return authKey;
	}
}
