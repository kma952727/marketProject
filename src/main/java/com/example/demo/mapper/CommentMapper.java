package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;

@Mapper
@Repository
public interface CommentMapper {
	
	public void insertComment(Comment comment);
	public List<Comment> selectComment(int index);
}
