package com.example.demo.model.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
public class PageMaker {
	
	private Criteria criteria;
	
	private int totalCount;
	private int startPage;
	private int endPage;
	private int LastPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10;
	public PageMaker(int totalCount, Criteria criteria) {
		this.totalCount = totalCount;
		this.criteria = criteria;
		calcData();
	}
	public void calcData() {
		endPage = (int) Math.ceil((double)criteria.getPageNum() /(double)displayPageNum) * displayPageNum;
		startPage =  (endPage - displayPageNum) + 1;
		LastPage = (int) Math.ceil((double)totalCount/(double)displayPageNum);
		if(endPage > LastPage && endPage - 10 < LastPage) endPage = LastPage;
		prev = startPage == 1? false : true;
	}
}
