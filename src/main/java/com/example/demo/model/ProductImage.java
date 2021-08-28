package com.example.demo.model;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class ProductImage {
	private int productId;
	private String serverImageName;
	private String originalImageName;
	private long size;
}
