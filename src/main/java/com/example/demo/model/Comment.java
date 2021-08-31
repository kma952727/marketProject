package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data @ToString
public class Comment {

	private Long commentId;
	private String accountName;
	private String contents;
	private LocalDateTime uploadTime;
	private Long boardId;
	private Long accountId;
}
