package com.example.demo.config;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.mail.SimpleEmailServiceImpl;

@Transactional
@SpringBootTest
public class MailTest {

	@Autowired SimpleEmailServiceImpl simpleEmailServiceImpl;
	
	@DisplayName("메일이 잘 보내지는가?")
	@Test
	public void sendMail() throws MessagingException {
		
	}
}
