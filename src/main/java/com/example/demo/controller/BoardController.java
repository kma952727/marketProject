package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.config.security.CustomUser;
import com.example.demo.model.Account;
import com.example.demo.model.Board;
import com.example.demo.model.form.BoardForm;
import com.example.demo.model.page.Criteria;
import com.example.demo.model.page.PageMaker;
import com.example.demo.service.AccountService;
import com.example.demo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board")
@Controller
public class BoardController {
	
	@Autowired AccountService accountService;
	@Autowired BoardService boardService;
	
	@GetMapping("/list/{index}")
	public String boardListView(@AuthenticationPrincipal CustomUser user, Model model
			, @PathVariable String index) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		Criteria criteria = new Criteria(Integer.parseInt(index));
		List<Board> boardList = boardService.getBoardList(criteria);
		PageMaker pageMaker = boardService.getPageMaker(criteria);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
		return "board/board_list";
	}
	
	@GetMapping("/upload")
	public String uploadBoardView(@AuthenticationPrincipal CustomUser user, Model model) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}
		model.addAttribute("boardForm", new BoardForm());
		return "board/board_upload";
	}
	@PostMapping("/upload")
	public String uploadBoard(@AuthenticationPrincipal CustomUser user, Model model, BoardForm boardForm) {
		if(user != null) {
			Account account = accountService.getAccountByName(user.getAccount().getUsername());
			model.addAttribute("account", account);
		}

		Board board = new Board();
		board.setAccountId(user.getAccount().getAccountId());
		board.setWriter(user.getAccount().getUsername());
		board.setSubject(boardForm.getSubject());
		board.setContents(boardForm.getContents());
		board.setUploadTime(LocalDateTime.now());
		boardService.uploadBoard(board);
		return "redirect:/";
	}
}
