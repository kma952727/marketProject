package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

@Mapper
@Repository
public interface ProductMapper {

	public void insertProduct(Product product);
	
}
