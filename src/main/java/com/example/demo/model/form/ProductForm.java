package com.example.demo.model.form;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class ProductForm {

	private String name;
	private int price;
	private String type;
	private String description;
	private int num;
	private int phoneNumber;
	private String endTime;
	private MultipartFile productImages;
	
}
