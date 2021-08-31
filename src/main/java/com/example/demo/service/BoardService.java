package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.Board;
import com.example.demo.model.page.Criteria;
import com.example.demo.model.page.PageMaker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BoardService {

	@Autowired BoardMapper boardMapper;
	
	public void uploadBoard(Board board) {
		boardMapper.insertBoard(board);
	}

	public List<Board> getBoardList(Criteria criteria) {
		int pageNum = criteria.getPageStart();
		List<Board> boardList = boardMapper.selectBoards(pageNum);
		return boardList;
	}

	public PageMaker getPageMaker(Criteria criteria) {
		int totalCount = boardMapper.selectTotalCount();
		PageMaker pageMaker = new PageMaker(totalCount, criteria);
		return pageMaker;
	}

	public Board getBoard(int index) {
		return boardMapper.selectBoard(index);
	}

	public void deleteBoard(int index) {
		boardMapper.deleteCommentJoinBoard(index);
		boardMapper.deleteBoard(index);
	}
}
