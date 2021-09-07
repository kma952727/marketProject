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
import com.example.demo.model.Comment;
import com.example.demo.model.CurrentAccount;
import com.example.demo.model.form.BoardForm;
import com.example.demo.model.form.CommentForm;
import com.example.demo.model.page.Criteria;
import com.example.demo.model.page.PageMaker;
import com.example.demo.service.AccountService;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board")
@Controller
public class BoardController {
	
	@Autowired AccountService accountService;
	@Autowired BoardService boardService;
	@Autowired CommentService commentService;
	
	@GetMapping("/list/{index}")
	public String boardListView(@CurrentAccount Account account, Model model
			, @PathVariable String index) {
		model.addAttribute("account", account);
		Criteria criteria = new Criteria(Integer.parseInt(index));
		List<Board> boardList = boardService.getBoardList(criteria);
		PageMaker pageMaker = boardService.getPageMaker(criteria);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
		return "board/board_list";
	}
	
	@GetMapping("/upload")
	public String uploadBoardView(@CurrentAccount Account account, Model model) {
		model.addAttribute("account", account);
		model.addAttribute("boardForm", new BoardForm());
		return "board/board_upload";
	}
	@PostMapping("/upload")
	public String uploadBoard(@CurrentAccount Account account, Model model, BoardForm boardForm) {
		model.addAttribute("account", account);

		Board board = new Board();
		board.setAccountId(account.getAccountId());
		board.setWriter(account.getUsername());
		board.setSubject(boardForm.getSubject());
		board.setContents(boardForm.getContents());
		board.setUploadTime(LocalDateTime.now());
		boardService.uploadBoard(board);
		return "redirect:/board/list/1";
	}
	
	@GetMapping("/detail/{index}")
	public String boardDetailView(@CurrentAccount Account account, 
			@PathVariable int index, Model model) {
		model.addAttribute("account", account);
		Board board = boardService.getBoard(index);
		List<Comment> commentList = commentService.getComment(index);
		model.addAttribute("board", board);
		model.addAttribute("commentForm", new CommentForm());
		model.addAttribute("commentList", commentList);
		model.addAttribute("account", account);
		return "board/board_detail";
	}
	@GetMapping("/delete/{index}")
	public String deleteBoard(@PathVariable int index) {
		boardService.deleteBoard(index);
		return "redirect:/board/list/1";
	}
	@GetMapping("/update/{index}")
	public String updateBoard(@CurrentAccount Account account, @PathVariable int index,
			Model model){
		
		model.addAttribute("account", account);		
		Board board = boardService.getBoard(index);
		model.addAttribute("board", board);
		model.addAttribute("boardForm", new BoardForm());
		return "board/board_upload";
	}
}
