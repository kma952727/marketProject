package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

/**
 * 자유 게시판 조회, 업로드, 삭제, 수정기능을 위한 url이
 * 맵핑된 컨트롤러입니다. 
 * 
 * @author cat95
 */
@Slf4j
@RequestMapping("/board")
@Controller
public class BoardController {
	final static String BOARD = "board";
	final static String LIST = "/list";
	final static String UPLOAD = "/upload";
	final static String DETAIL = "/detail";
	final static String UPDATE = "/update";
	final static String DELETE = "/delete";
	final static String BOARD_LIST = "/board_list";
	final static String BOARD_UPLOAD = "/board_upload";
	final static String BOARD_DETAIL = "/board_detail";
	final static String REDIRECT ="redirect:/";
	
	final static String START = "/1";
	
	@Autowired AccountService accountService;
	@Autowired BoardService boardService;
	@Autowired CommentService commentService;
	
	@GetMapping(LIST + "/{index}")
	public String boardListView(@CurrentAccount Account account, Model model
			, @PathVariable String index) {
		
		Criteria criteria = new Criteria(Integer.parseInt(index));
		PageMaker pageMaker = boardService.getPageMaker(criteria);
		List<Board> boardList = boardService.getBoardList(criteria);
		
		model.addAttribute("account", account);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("boardList", boardList);
		
		return BOARD + BOARD_LIST;
	}
	
	@GetMapping(UPLOAD)
	public String uploadBoardView(@CurrentAccount Account account, Model model) {
		
		model.addAttribute("account", account);
		model.addAttribute("boardForm", new BoardForm());
		return BOARD + BOARD_UPLOAD;
	}
	
	@PostMapping(UPLOAD)
	public String uploadBoard(@CurrentAccount Account account, Model model, BoardForm boardForm) {
		
		Board board = new Board();
		board.setAccountId(account.getAccountId());
		board.setWriter(account.getUsername());
		board.setSubject(boardForm.getSubject());
		board.setContents(boardForm.getContents());
		board.setUploadTime(LocalDateTime.now());
		boardService.uploadBoard(board);
		
		model.addAttribute("account", account);
		return REDIRECT + BOARD + LIST + START;
	}
	
	@GetMapping(DETAIL + "/{index}")
	public String boardDetailView(@CurrentAccount Account account, 
			@PathVariable int index, Model model) {
		
		Board board = boardService.getBoard(index);
		List<Comment> commentList = commentService.getComment(index);
		
		model.addAttribute("account", account);
		model.addAttribute("board", board);
		model.addAttribute("commentForm", new CommentForm());
		model.addAttribute("commentList", commentList);
		model.addAttribute("account", account);
		
		return BOARD + BOARD_DETAIL;
	}
	
	@DeleteMapping(DELETE + "/{index}")
	public String deleteBoard(@PathVariable int index) {
		boardService.deleteBoard(index);
		return REDIRECT + BOARD + LIST + START;
	}
	
	@GetMapping(UPDATE + "/{index}")
	public String updateBoard(@CurrentAccount Account account, @PathVariable int index,
			Model model){
				
		Board board = boardService.getBoard(index);
		
		model.addAttribute("boardForm", new BoardForm());
		model.addAttribute("account", account);
		model.addAttribute("board", board);
		return BOARD + BOARD_UPLOAD;
	}
}
