package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString 
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	private Long boardId;
	private Long accountId;
	private String writer;
	private String subject;
	private String contents;
	private LocalDateTime uploadTime;
	private Long hits;
	List<Comment> commentList;
	
}
