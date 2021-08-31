package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Board;
import com.example.demo.model.page.Criteria;

@Mapper
@Repository
public interface BoardMapper {

	public void insertBoard(Board board);
	public List<Board> selectBoards(int index);
	public List<Board> testPaging(Criteria criteria);
	public int selectTotalCount();
}
