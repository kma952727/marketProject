package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.config.CustomUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@GetMapping("/")
	public String main(@AuthenticationPrincipal CustomUser user) {
		if(user != null) {
			log.info("값:" + user.getAccount().getUsername());
			log.info("값:" + user.getAccount().getPassword());
			log.info("값:" + user.getAccount().getMail());
			log.info("값:" + user.getAccount().getIsEmailverified());
		}
		return "index";
	}
}
