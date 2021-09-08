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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.security.CustomUser;
import com.example.demo.file.FileUtils;
import com.example.demo.model.Account;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.model.Account.Builder;
import com.example.demo.model.Product.ProductBuilder;
import com.example.demo.model.form.ProductForm;
import com.example.demo.model.form.PurchaseForm;
import com.example.demo.service.AccountService;
import com.example.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 상품 업로드, 조회, 좋아요기능을 위한 url이
 * 맵핑된 컨트롤러입니다.
 * 
 * @author cat95
 */
@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
	
	final static String ROOT = "/";
	final static String INDEX = "index";
	final static String PRODUCT = "product";
	final static String UPLOAD = "/upload";
	final static String CATEGORI = "/categori";
	final static String LIKE = "/like";
	final static String SEARCH = "/search";
	final static String PRODUCT_UPLOAD = "/product_upload";
	final static String PRODUCT_DETAIL = "/product_detail";
	final static String REDIRECT = "redirect:/";
	
	@Autowired AccountService accountService;
	@Autowired ProductService productService;
	@Autowired FileUtils fileUtils;
	
	@GetMapping(UPLOAD)
	public String uploadProductView(@CurrentAccount Account account, Model model) {
		model.addAttribute("account", account);
		model.addAttribute("productForm",new ProductForm());
		return PRODUCT + PRODUCT_UPLOAD;
	}
	
	/**
	 * 상품업로드기능을 하는 함수입니다.
	 * 
	 * @param account 현재 로그인되어있는 유저입니다.
	 * @param file 상품사진이 담긴 배열입니다.
	 * @param productForm 상품정보가 바인딩되어있습니다.
	 * @param model
	 * @return
	 */
	@PostMapping(UPLOAD)
	public String uploadProduct(@CurrentAccount Account account, 
			@RequestParam("file") MultipartFile[] file, ProductForm productForm, 
			Model model) {
		
		LocalDateTime endTime = StringToDate(productForm.getEndTime());
		Product product = new ProductBuilder()
				.setName(productForm.getName())
				.setPrice(productForm.getPrice())
				.setType(productForm.getType())
				.setDescription(productForm.getDescription())
				.setNum(productForm.getNum())
				.setEndTime(endTime)
				.setPhoneNumber(productForm.getPhoneNumber())
				.setProductImages(file)
				.setAccountId(account.getAccountId())
				.build();
		
		//'MultipartFile'배열을 db에 입력을위한 VO배열로 바꿉니다.
		List<ProductImage> productImages = fileUtils.convertImageToModel(file);
		productService.uploadProduct(product, productImages);
		
		model.addAttribute("account", account);
		return REDIRECT;
	}
	
	/**
	 * 상품 상세화면을 보여줍니다.
	 * 
	 * @param account 현재 로그인한 유저입니다.
	 * @param productId 상세화면으로 보여줄 상품의 id값입니다.
	 * @param model
	 * @return
	 */
	@GetMapping("/{productId}")
	public String productView(@CurrentAccount Account account, 
			@PathVariable("productId") int productId, Model model) { 
		
		Product product = productService.getProduct(productId);
		//조회수를 1추가합니다.
		productService.countHits(productId);
		//상품 이미지경로배열중 첫번째값을 썸네일이미지로 사용합니다.
		String thumbnailImageName = product.getProductImageList().get(0).getServerImageName();
		
		model.addAttribute("product",product);
		model.addAttribute("thumbnailImageName", thumbnailImageName);
		model.addAttribute("purchaseForm", new PurchaseForm());
		model.addAttribute("account", account);
		return PRODUCT + PRODUCT_DETAIL;
	}
	
	/**
	 * 상품목록을 보여주는 기능입니다. type을 필터로 사용해서
	 * 종류별로 상품들을 볼수있습니다.
	 * 
	 * @param account 현재 로그인한 유저입니다.
	 * @param model
	 * @param type 상품종류입니다. 해당 상품종류로만 상품을 리스트에 보여줍니다. 
	 * @param index 
	 * @return
	 */
	@GetMapping(CATEGORI + "/{type}/{index}")
	public String categoriView(@CurrentAccount Account account, Model model,
			@PathVariable String type, @PathVariable Integer index) {
		
		List<Product> productList = productService.getProductList(index, type);
		
		//상품이 존재하면 모델에 넣습니다.
		if(!productList.isEmpty())
			model.addAttribute("productList", productList);
		model.addAttribute("account", account);
		return INDEX;
	}
	
	/**
	 * 검색기능으로 상품을 검색합니다.
	 * 
	 * @param account 현재 로그인한 유저입니다.
	 * @param model
	 * @param keyword 해당 파라미터를 포함한 상품을 검색합니다.
	 * @return
	 */
	@GetMapping(CATEGORI + SEARCH + "/{keyword}")
	public String categoriSearch(@CurrentAccount Account account, Model model,
			@PathVariable String keyword) {
		
		List<Product> productList = productService.getProductList(keyword);
		
		model.addAttribute("productList", productList);
		model.addAttribute("account", account);
		return INDEX;
	}
	
	/**
	 * 상품 좋아요기능입니다.
	 * 
	 * @param account
	 * @param index 좋아요할 상품의 id값입니다.
	 * @param redirectAttributes
	 * @return
	 */
	@PutMapping(LIKE + "/{index}")
	public String likeProduct(@CurrentAccount Account account, @PathVariable int index,
			RedirectAttributes redirectAttributes) {
		boolean isSuccess = productService.likeProduct(account.getAccountId(), index);
		//좋아요를 할수없는 상태면은 'isSuccess'에 false입니다, 'isLikeSuccess'값이 false면은 화면에서 표시해줍니다.
		if(!isSuccess)
			redirectAttributes.addFlashAttribute("isLikeSuccess", false);
			
		return REDIRECT + PRODUCT + ROOT + index;
	}
	
	/**
	 * 문자열타입의 시간값을 'LocalDateTime'으로 변환합니다.
	 * 
	 * @param StringDate '/'가 구분자로 되어있는 문자열타입의 년월도값입니다.
	 * @return
	 */
	private LocalDateTime StringToDate(String StringDate) {
		String[] imageNames =  StringDate.split(ROOT);
		LocalDateTime endTime = 
				LocalDateTime.of(Integer.parseInt(imageNames[0]),
						Integer.parseInt(imageNames[1]), Integer.parseInt(imageNames[2]), 0, 0, 0);
		return endTime;
	}
}
