package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.config.security.CustomUser;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.service.AccountService;
import com.example.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

	final static int START_PAGE = 0;
	final static String ROOT = "/";
	final static String INDEX = "index";
	final static String ALL = "all";

	@Autowired private AccountService accountService;
	@Autowired private ProductService productService;
	
	@GetMapping(ROOT)
	public String main(@CurrentAccount Account currentAccount, Model model) {
		if(currentAccount != null)
		currentAccount = accountService.getAccountByName(currentAccount.getUsername());
		List<Product> productList = productService.getProductList(START_PAGE, ALL);
		model.addAttribute("productList", productList);
		model.addAttribute("account", currentAccount);
		return INDEX;
	}

}
