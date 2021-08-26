package com.example.demo.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.validate.RegisterValidator;
import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;
import com.example.demo.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class AccountController {

	@Autowired private AccountService accountService;
	@Autowired private RegisterValidator registerValidator;
	
	@InitBinder("registerForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(registerValidator);
	}
	
	@GetMapping("/signin")
	public String signin_view(Model model) {
		model.addAttribute("signinForm", new SigninForm());
		return "signin";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	}
	@GetMapping("/register")
	public String register_view(Model model) {
		model.addAttribute(new RegisterForm());
		return "register";
	}
	@PostMapping("/register")
	public String register(@Valid RegisterForm registerForm,
			BindingResult bindingResult) throws MessagingException {
		if(bindingResult.hasErrors()) {
			return "register";
		}
		Account account = new Account.Builder()
				.setName(registerForm.getName())
				.setPassword(registerForm.getPassword())
				.setMail(registerForm.getMail())
				.build();
		accountService.register(account);
		return "redirect:/";
	}
	@GetMapping("/emailConfirm")
	public String email_confirm(String username, String authKey) {
		log.info("confirm!"+ authKey);
		accountService.emailConfirm(username, authKey);
		return "redirect:/";
	}
	@GetMapping("/emailSend/{username}")
	public String email_send(@PathVariable String username,
			RedirectAttributes redirectAttributes) throws MessagingException {
		accountService.email_send(username);
		redirectAttributes.addAttribute("isSend", "send");
		return "redirect:/";
	}
}
