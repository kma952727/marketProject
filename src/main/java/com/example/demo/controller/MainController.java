package com.example.demo.controller;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.config.security.CustomUser;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	@Autowired AccountService accountService;
	
	@GetMapping("/")
	public String main(@AuthenticationPrincipal CustomUser user, Model model) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		return "index";
	}
	

}
