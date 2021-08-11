package com.example.demo.config;

import static org.junit.Assert.assertTrue;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;




@Transactional
@SpringBootTest
public class EncodingTest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@DisplayName("회원가입시 패스워드 인코딩이 잘되는가?")
	@Test
	public void regist_with_pass() {
		
		String originalPassword = "a123ekrj@@#1";
		//SHA512
		String encode512Password = DigestUtils.sha512Hex(originalPassword);
		String encode512Password2 = DigestUtils.sha512Hex(originalPassword);
		//SHA256
		String encode256Password = DigestUtils.sha256Hex(originalPassword);
		String encode256Password2 = DigestUtils.sha256Hex(originalPassword);
		//Bcrypt암호화
		PasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encodeBcryptEncode1 =  encoder.encode(originalPassword);
		String encodeBcryptEncode2 =  encoder.encode(originalPassword);
		
		assertTrue(encoder.matches(originalPassword, encodeBcryptEncode1));
		assertTrue(encoder.matches(originalPassword, encodeBcryptEncode2));
	}
}
