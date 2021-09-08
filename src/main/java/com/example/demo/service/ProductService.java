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
	
	//'좋아요'기능 사용을 위한 조건에서 사용됩니다.
	final static String TRUE = "y";
	
	@Autowired private ProductMapper productMapper;
	@Autowired private FileUtils fileUtils;
	
	public void uploadProduct(Product product, List<ProductImage> productImages) {
		//로컬, 서버에 파일을 저장합니다.
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
		//좋아요 기능을 사용할수있으면 'y'값이 할당됩니다.
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
