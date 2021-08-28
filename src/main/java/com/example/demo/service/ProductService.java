package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.FileUtils;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;

@Service
public class ProductService {

	@Autowired private ProductMapper productMapper;
	@Autowired private FileUtils fileUtils;
	
	public void upload_product(Product product) {
		fileUtils.saveFileToDisk(product.getProductImages());
		productMapper.insertProduct(product);
	}
}
