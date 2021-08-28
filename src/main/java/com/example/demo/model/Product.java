package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class Product {
	
	private int ProductId;
	private String name;
	private int price;
	private String type;
	private String description;
	private int num;
	private int phoneNumber;
	private LocalDateTime uploadTime = LocalDateTime.now();
	private LocalDateTime endTime;
	private MultipartFile[] productImages;
	private String serverImageName;
	private String originalImageName;
	
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
		
		public Product build() {
			return new Product(this.name, this.price, this.type,
					this.description, this.num, this.phoneNumber,
					this.endTime, this.productImages);
		}
	}
	private Product(String name, int price, String type,
			String description, int num, int phoneNumber,
			LocalDateTime endTime, MultipartFile[] productImages) {
			this.name = name;
			this.price = price;
			this.type = type;
			this.description = description;
			this.num = num;
			this.phoneNumber = phoneNumber;
			this.endTime = endTime;
			this.productImages = productImages;
	}
}
