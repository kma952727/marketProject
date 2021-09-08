package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.config.security.CustomUser;
import com.example.demo.model.Account;
import com.example.demo.model.Comment;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.form.CommentForm;
import com.example.demo.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
	final static String ROOT = "/";
	final static String BOARD = "board";
	final static String UPLOAD = "/upload";
	final static String DETAIL = "/detail";
	final static String REDIRECT ="redirect:/";
	
	@Autowired CommentService commentService;
	
	@PostMapping(UPLOAD)
	public String uploadComment(@CurrentAccount Account account, CommentForm commentForm) {
		Comment comment = new Comment();
		comment.setAccountName(account.getUsername());
		comment.setAccountId(commentForm.getAccountId());
		comment.setBoardId(commentForm.getBoardId());
		comment.setContents(commentForm.getContents());
		comment.setUploadTime(LocalDateTime.now());
		commentService.uploadComment(comment);
		return REDIRECT + BOARD + DETAIL + ROOT + commentForm.getBoardId();
	}
}
