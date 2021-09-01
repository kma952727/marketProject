package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Purchase;

@Mapper
@Repository
public interface PurchaseMapper {

	public void insertPurchase(Purchase purchase);

}
