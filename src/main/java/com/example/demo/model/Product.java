package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString 
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private Integer productId;
	private String name;
	private Integer price;
	private String type;
	private String description;
	private Long hits;
	private Integer num;
	private Integer phoneNumber;
	private Integer like;
	private LocalDateTime uploadTime = LocalDateTime.now();
	private LocalDateTime endTime;
	private MultipartFile[] productImages;
	private List<ProductImage> productImageList;
	private Integer productImageId;
	private String serverImageName;
	private String originalImageName;
	private Integer size;
	private Long accountId;
	
	public static class ProductBuilder {
		
		private String name;
		private int price;
		private String type;
		private String description;
		private int num;
		private int phoneNumber;
		private LocalDateTime uploadTime;
		private LocalDateTime endTime;
		private MultipartFile[] productImages;
		private String serverImageName;
		private String originalImageName;
		private int size;
		private Long accountId;
		
		public ProductBuilder setName(String name) {
			this.name = name;
			return this;
		}
		public ProductBuilder setPrice(int price) {
			this.price = price;
			return this;
		}
		public ProductBuilder setType(String type) {
			this.type = type;
			return this;
		}
		public ProductBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		public ProductBuilder setNum(int num) {
			this.num = num;
			return this;
		}
		public ProductBuilder setPhoneNumber(int phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		public ProductBuilder setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
			return this;
		}
		public ProductBuilder setProductImages(MultipartFile[] productImages) {
			this.productImages = productImages;
			return this;
		}
		public ProductBuilder setAccountId(Long accountId) {
			this.accountId = accountId;
			return this;
		}
		
		public Product build() {
			return new Product(this.name, this.price, this.type,
					this.description, this.num, this.phoneNumber,
					this.endTime, this.productImages, this.accountId);
		}
	}
	private Product(String name, int price, String type,
			String description, int num, int phoneNumber,
			LocalDateTime endTime, MultipartFile[] productImages, Long accountId) {
			this.name = name;
			this.price = price;
			this.type = type;
			this.description = description;
			this.num = num;
			this.phoneNumber = phoneNumber;
			this.endTime = endTime;
			this.productImages = productImages;
			this.accountId = accountId;
	}
	public Product(Integer productId, String name, Integer price, String type,
			String description, Integer num, Integer like, LocalDateTime uploadTime,
			LocalDateTime endTime, Integer phoneNumber, Integer productImageId,
			String originalImageName, String serverImageName, Integer size, Long accountId) {
			this.productId = productId;
			this.name = name;
			this.price = price;
			this.type = type;
			this.description = description;
			this.num = num;
			this.like = like;
			this.uploadTime = uploadTime;
			this.endTime = endTime;
			this.phoneNumber = phoneNumber;
			this.productImageId = productImageId;
			this.originalImageName = originalImageName;
			this.serverImageName = serverImageName;
			this.size = size;
			this.accountId = accountId;
	}
}
