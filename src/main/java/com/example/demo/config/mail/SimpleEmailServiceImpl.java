package com.example.demo.config.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleEmailServiceImpl {
	
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private MailKeyGenerator mailKeyGenerator;
	private String authKey;
	
	public String sendMail(String to, String username) throws MessagingException {
		authKey = mailKeyGenerator.getKey(50, false);
		String body = "<html>"
				+ "<body><h1>링크를 클릭해서 인증해주세요.</h1>" +
				"<a href='http://kma95277.cafe24.com/emailConfirm?username="
				+username+"&authKey="+authKey+"'>인증링크!</a> "
						+ "<body>"
						+ "</html>";
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject("이메일 인증 메일");
		message.setRecipient(Message.RecipientType.TO, 
				new InternetAddress(to));
		message.setContent(body, "text/html;charset=UTF-8");
		message.setFrom("FreeMarket");
		message.setSentDate(new Date());
		javaMailSender.send(message);
		log.info("메일전송");
		return authKey;
	}
}
