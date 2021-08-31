package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Board;

@Mapper
@Repository
public interface BoardMapper {

	public void insertBoard(Board board);
	public List<Board> selectBoards(String index);
}
