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
import com.example.demo.service.PurchaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/purchase")
@Controller
public class PurchaseController {
	
	@Autowired AccountService accountService;
	@Autowired PurchaseService purchasService;
	
	@GetMapping("/list")
	public String purchaseList(@CurrentAccount Account account, Model model) {
		
		model.addAttribute("account", account);
		
		List<Product> productList = purchasService.getPurchaseList(account.getAccountId());
		model.addAttribute("productList", productList);
		return "purchase/purchase_list";
	}
	
	@PostMapping("/{productId}")
	public String purchase(@CurrentAccount Account account, PurchaseForm purchaseForm
			,@PathVariable int productId) {
		Purchase purchase = new Purchase();
		purchase.setAccountId(account.getAccountId());
		purchase.setProductId(productId);
		purchase.setAmount(purchaseForm.getAmount());
		purchasService.purchaseProduct(purchase);
		return "redirect:/purchase/list";
	}
	@GetMapping("/cancel/{productId}")
	public String cancelPurchase() {
		return "redirect:list";
	}
}
