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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.validate.RegisterValidator;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;
import com.example.demo.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원가입, 로그인, 로그아웃처리를 위한 url이
 * 맵핑된 컨트롤러입니다.
 * 
 * @author cat95
 */
@Slf4j
@RequestMapping("/account")
@Controller
public class AccountController {

	final static String ROOT = "/";
	final static String SIGNIN = "signin";
	final static String REGISTER = "register";
	final static String LOGOUT = "/logout";
	final static String REDIRECT = "redirect:/";
	
	@Autowired private AccountService accountService;
	@Autowired private RegisterValidator registerValidator;
	
	@InitBinder("registerForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(registerValidator);
	}
	
	@GetMapping(ROOT + SIGNIN)
	public String signinView(Model model) {
		model.addAttribute("signinForm", new SigninForm());
		return "SIGNIN";
	}
	
	@DeleteMapping(LOGOUT)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null)
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    
	    return REDIRECT;
	}
	
	@GetMapping(ROOT + REGISTER)
	public String registerView(Model model) {
		model.addAttribute(new RegisterForm());
		return REGISTER;
	}
	
	@PostMapping(ROOT + REGISTER)
	public String register(@Valid RegisterForm registerForm,
			BindingResult bindingResult) throws MessagingException {
		
		if(bindingResult.hasErrors()) return REGISTER;
		
		Account account = new Account.Builder()
				.setName(registerForm.getName())
				.setPassword(registerForm.getPassword())
				.setMail(registerForm.getMail())
				.build();
		accountService.register(account);
		return REDIRECT;
	}
}
