package com.example.demo.model.form;

import java.time.LocalDateTime;

import groovy.transform.builder.Builder;
import lombok.Data;
import lombok.ToString;

@Data @ToString
public class BoardForm {
	
	private Long boardId;
	private String writer;
	private String subject;
	private String contents;
	private LocalDateTime uploadTime;
	private Long hits;
	
}
