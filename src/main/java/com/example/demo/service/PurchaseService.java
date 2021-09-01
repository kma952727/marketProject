package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.PurchaseMapper;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PurchaseService {
	
	@Autowired PurchaseMapper purchaseMapper;
	@Autowired ProductMapper productMapper;
	
	public void purchaseProduct(Purchase purchase) {
		purchaseMapper.insertPurchase(purchase);
		productMapper.updateProductNum(purchase);
	}

	public List<Product> getPurchaseList(Long accountId) {
		return productMapper.selectParchase(accountId);
	}

}
