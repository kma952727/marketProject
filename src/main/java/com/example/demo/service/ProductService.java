package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.FileUtils;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ProductService {

	@Autowired private ProductMapper productMapper;
	@Autowired private FileUtils fileUtils;
	
	public void upload_product(Product product, List<ProductImage> productImages) {
		fileUtils.saveFileToDisk(product.getProductImages());
		productMapper.insertProduct(product);
		productMapper.insertProductImage(productImages, product.getProductId());
	}
	
	public List<Product> getProductList(int index, int[] option){
		List<Product> productList
			= productMapper.selectProducts(index);
		return productList;
	}
}
