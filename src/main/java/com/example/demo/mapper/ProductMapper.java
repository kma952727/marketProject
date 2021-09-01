package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
import com.example.demo.model.ProductImage;
import com.example.demo.model.Purchase;

@Mapper
@Repository
public interface ProductMapper {

	public void insertProduct(Product product);
	public void insertProductImage(@Param("list") List<ProductImage> list, 
			@Param("productId") int proudctId);
	public List<Product> selectProducts(int id, String type);
	public List<Product> searchProducts(String keyword);
	public Product selectProductById(int productId);
	public void countHits(int productId);
	public void updateProductLike(Long accountId, int index);
	public String selectProductLike(Long accountId, int productId);
	public void updateProductNum(Purchase purchase);
	public List<Product> selectParchase(Long accountId);
}
