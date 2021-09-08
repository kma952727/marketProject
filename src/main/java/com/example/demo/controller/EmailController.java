package com.example.demo.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 이메일 확인, 전송기능을 위한 url이
 * 맵핑된 컨트롤러 입니다.
 * 
 * 
 * @author cat95
 */
@Slf4j
@Controller
@RequestMapping("/email")
public class EmailController {
	
	final static String CONFIRM = "/confirm";
	final static String SEND = "/send";
	final static String REDIRECT = "redirect:/";
	@Autowired private AccountService accountService;
	
	/**
	 * 두개의 파라미터 'username'에 해당하는 테이블의 'auth_key' 컬럼값과 authKey이 매칭이되는지 확인합니다.
	 * 
	 * @param username 인증메일에 담긴 유저이름입니다.
	 * @param authKey 인증메일에 담긴 인증값입니다.
	 * @return
	 */
	@GetMapping(CONFIRM)
	public String emailConfirm(String username, String authKey) {
		accountService.emailConfirm(username, authKey);
		return REDIRECT;
	}
	@GetMapping(SEND + "/{username}")
	public String emailSend(@PathVariable String username,
			RedirectAttributes redirectAttributes) throws MessagingException {
		accountService.emailSend(username);
		redirectAttributes.addAttribute("isSend", "send");
		return REDIRECT;
	}
}
