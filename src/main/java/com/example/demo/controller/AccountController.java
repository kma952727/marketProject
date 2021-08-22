package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;
import com.example.demo.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
	
	@GetMapping("/signin")
	public String signin_view(Model model) {
		model.addAttribute("signinForm", new SigninForm());
		return "signin";
	}
	
	@GetMapping("/register")
	public String register_view(Model model) {
		model.addAttribute(new RegisterForm());
		return "register";
	}
	@PostMapping("/register")
	public String register(RegisterForm registerForm) {

		Account account = new Account.Builder()
				.setName(registerForm.getName())
				.setPassword(registerForm.getPassword())
				.setMail(registerForm.getMail())
				.build();
		accountService.register(account);
		return "redirect:/";
	}
}
