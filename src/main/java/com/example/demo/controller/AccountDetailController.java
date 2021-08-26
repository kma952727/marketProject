package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountDetailController {

	@GetMapping("/detail/{name}")
	public String account_detail_view() {
		return "detail";
	}
}
