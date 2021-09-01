package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.FileUtils;
import com.example.demo.config.security.CustomUser;
import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.model.Account.Builder;
import com.example.demo.model.Product.ProductBuilder;
import com.example.demo.model.form.ProductForm;
import com.example.demo.service.AccountService;
import com.example.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired AccountService accountService;
	@Autowired ProductService productService;
	@Autowired FileUtils fileUtils;
	
	@GetMapping("/upload")
	public String upload_product_view(@AuthenticationPrincipal CustomUser user, Model model) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		model.addAttribute("productForm",new ProductForm());
		return "product/product_upload";
	}
	
	@PostMapping("/upload")
	public String upload_product(@AuthenticationPrincipal CustomUser user, 
			@RequestParam("file") MultipartFile[] file, ProductForm productForm, 
			Model model) {
		Account account = null;
		if(user != null) {
			account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		LocalDateTime endTime = StringToDate(productForm.getEndTime());
		log.info("값추출"+user.getAccount().getAccountId());
		Product product = new ProductBuilder()
				.setName(productForm.getName())
				.setPrice(productForm.getPrice())
				.setType(productForm.getType())
				.setDescription(productForm.getDescription())
				.setNum(productForm.getNum())
				.setEndTime(endTime)
				.setPhoneNumber(productForm.getPhoneNumber())
				.setProductImages(file)
				.setAccountId(user.getAccount().getAccountId())
				.build();
		List<ProductImage> productImages = fileUtils.convertImageToModel(file);
		productService.upload_product(product, productImages);
		
		return "redirect:/";
	}
	
	@GetMapping("/{productId}")
	public String productView(@AuthenticationPrincipal CustomUser user, 
			@PathVariable("productId") int productId, Model model) { 
		Account account = null;
		if(user != null) {
			account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
			model.addAttribute(account);
		}
		Product product = productService.getProduct(productId);
		productService.countHits(productId);
		String thumbnailImageName = product.getProductImageList().get(0).getServerImageName();
		model.addAttribute(product);
		model.addAttribute("thumbnailImageName", thumbnailImageName);
		return "product/product_detail";
	}
	
	@GetMapping("/categori/{type}/{index}")
	public String catagoriView(@AuthenticationPrincipal CustomUser user, Model model,
			@PathVariable String type, @PathVariable Integer index) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		List<Product> productList = productService.getProductList(index, type);
		model.addAttribute("productList", productList);
		
		return "index";
	}
	@GetMapping("/like/{index}")
	public String likeProduct(@AuthenticationPrincipal CustomUser user, @PathVariable int index,
			RedirectAttributes redirectAttributes) {
		boolean isSuccess = productService.likeProduct(user.getAccount().getAccountId(), index);
		if(!isSuccess)
			redirectAttributes.addFlashAttribute("isLikeSuccess", false);
			
		return "redirect:/product/"+index;
	}
	private LocalDateTime StringToDate(String StringDate) {
		String[] imageNames =  StringDate.split("/");
		LocalDateTime endTime = 
				LocalDateTime.of(Integer.parseInt(imageNames[0]),
						Integer.parseInt(imageNames[1]), Integer.parseInt(imageNames[2]), 0, 0, 0);
		return endTime;
	}
}
