package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

	@GetMapping("/signin")
	public String signin_view() {
		return "signin";
	}
	@PostMapping("/signin")
	public String signin() {
		return "redirect:/";
	}
	@GetMapping("/signup")
	public String signup_view() {
		return "signup";
	}
	@PostMapping("/signup")
	public String signup() {
		return "redirect:/";
	}
}
