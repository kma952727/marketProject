package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.file.FileUtils;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class ProductService {
	
	final static String TRUE = "y";
	
	@Autowired private ProductMapper productMapper;
	@Autowired private FileUtils fileUtils;
	
	public void uploadProduct(Product product, List<ProductImage> productImages) {
		fileUtils.saveFileToDisk(product.getProductImages(), productImages);
		productMapper.insertProduct(product);
		productMapper.insertProductImage(productImages, product.getProductId());
	}
	
	public List<Product> getProductList(int index, String type){
		List<Product> productList
			= productMapper.selectProducts(index, type);
		return productList;
	}
	public List<Product> getProductList(String keyword){
		List<Product> productList
			= productMapper.searchProducts(keyword);
		return productList;
	}
	
	public Product getProduct(int productId) {
		return productMapper.selectProductById(productId);
	}

	public void countHits(int productId) {
		productMapper.countHits(productId);
		
	}

	public boolean likeProduct(Long accountId, int index) {
		String alreayLike = isExistsProductLike(accountId, index);
		if(alreayLike.equals(TRUE)) {
			productMapper.updateProductLike(accountId, index);
			return true;
		}
		
		return false;
	}
	private String isExistsProductLike(Long accountId, int productId) {
		return productMapper.selectProductLike(accountId, productId);
	}

}
