package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {

	@Autowired BoardMapper boardMapper;
	
	public void uploadBoard(Board board) {
		boardMapper.insertBoard(board);
	}

	public List<Board> getBoardList(String index) {
		
		List<Board> boardList = boardMapper.selectBoards(index);
		log.info(boardList.toString());
		return boardList;
	}

}
