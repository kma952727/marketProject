package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.config.security.CustomUser;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.form.PurchaseForm;
import com.example.demo.service.AccountService;
import com.example.demo.service.ProductService;
import com.example.demo.service.PurchaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/purchase")
@Controller
public class PurchaseController {
	
	final static String PURCHASE = "purchase";
	final static String LIST = "/list";
	final static String DETAIL = "/detail";
	final static String PURCHASE_LIST = "/purchase_list";
	final static String PURCHASE_DETAIL = "/purchase_detail";
	final static String REDIRECT = "redirect:/";
	
	@Autowired AccountService accountService;
	@Autowired PurchaseService purchasService;
	@Autowired ProductService productService;
	
	@GetMapping(LIST)
	public String purchaseList(@CurrentAccount Account account, Model model) {
		
		model.addAttribute("account", account);
		
		List<Product> productList = purchasService.getPurchaseList(account.getAccountId());
		model.addAttribute("productList", productList);
		return PURCHASE + PURCHASE_LIST;
	}
	
	@PostMapping("/{productId}")
	public String purchase(@CurrentAccount Account account, PurchaseForm purchaseForm
			,@PathVariable int productId) {
		Purchase purchase = new Purchase();
		purchase.setAccountId(account.getAccountId());
		purchase.setProductId(productId);
		purchase.setAmount(purchaseForm.getAmount());
		purchasService.purchaseProduct(purchase);
		return REDIRECT + PURCHASE + LIST;
	}
	
	@GetMapping(DETAIL + "/{productId}")
	public String purchaseView(@CurrentAccount Account account, 
			@PathVariable int productId, Model model) {
		Product product = productService.getProduct(productId);
		String thumbnailImageName = product.getProductImageList().get(0).getServerImageName();
		model.addAttribute("product",product);
		model.addAttribute("thumbnailImageName", thumbnailImageName);
		model.addAttribute("account", account);
		return PURCHASE + PURCHASE_DETAIL;
	}
}
