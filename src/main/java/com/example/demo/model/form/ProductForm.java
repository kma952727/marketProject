package com.example.demo.model.form;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductForm {

	private String name;
	private String price;
	private String type;
	private String description;
	private int num;
	private int phoneNumber;
	private LocalDateTime endTime;
}
