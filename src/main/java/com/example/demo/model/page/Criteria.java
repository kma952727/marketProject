package com.example.demo.model.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum;
	private int amount;
	
	public Criteria() {
		this.pageNum = 1;
		this.amount = 10;
	}
	
	public Criteria(int pageNum) {
		if(pageNum < 1) pageNum = 1;
		this.pageNum = pageNum;
		this.amount = 10;
	}
	public int getPageStart() {
		return (pageNum - 1) * amount;
	}
	
	
}
