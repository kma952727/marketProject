package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Purchase {
	
	private int purchaseId;
	private int productId;
	private Long accountId;
	private int amount;
}
