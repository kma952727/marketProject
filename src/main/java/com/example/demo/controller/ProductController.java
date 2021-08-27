package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.CustomUser;
import com.example.demo.model.Account;
import com.example.demo.model.form.ProductForm;
import com.example.demo.service.AccountService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired AccountService accountService;
	
	@GetMapping("/upload")
	public String upload_product(@AuthenticationPrincipal CustomUser user, Model model) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		model.addAttribute("productForm",new ProductForm());
		return "product/product_upload";
	}
}
