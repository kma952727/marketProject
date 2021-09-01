package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.FileUtils;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class ProductService {

	@Autowired private ProductMapper productMapper;
	@Autowired private FileUtils fileUtils;
	
	public void upload_product(Product product, List<ProductImage> productImages) {
		fileUtils.saveFileToDisk(product.getProductImages(), productImages);
		productMapper.insertProduct(product);
		productMapper.insertProductImage(productImages, product.getProductId());
	}
	
	public List<Product> getProductList(int index, String type){
		List<Product> productList
			= productMapper.selectProducts(index, type);
		return productList;
	}
	
	public Product getProduct(int productId) {
		return productMapper.selectProductById(productId);
	}

	public void countHits(int productId) {
		productMapper.countHits(productId);
		
	}

	public boolean likeProduct(Long accountId, int index) {
		log.info("accountid 값"+accountId);
		String alreayLike = isExistsProductLike(accountId, index);
		if(alreayLike.equals("y")) {
			productMapper.updateProductLike(accountId, index);
			return true;
		}
		
		return false;
	}
	private String isExistsProductLike(Long accountId, int productId) {
		return productMapper.selectProductLike(accountId, productId);
	}

}
