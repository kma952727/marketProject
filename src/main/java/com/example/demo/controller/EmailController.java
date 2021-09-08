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

@Slf4j
@Controller
@RequestMapping("/email")
public class EmailController {
	
	final static String CONFIRM = "/confirm";
	final static String SEND = "/send";
	final static String REDIRECT = "redirect:/";
	@Autowired private AccountService accountService;
	
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
