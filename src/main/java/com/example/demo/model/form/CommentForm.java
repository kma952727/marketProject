package com.example.demo.model.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommentForm {
	
	private Long boardId;
	private Long accountId;
	private String contents;
}
