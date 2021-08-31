package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;

@Service
@Transactional
public class CommentService {
	
	@Autowired CommentMapper commentMapper;
	
	public void uploadComment(Comment comment) {
		
		commentMapper.insertComment(comment);
	}
	
	public List<Comment> getComment(int index){
		return commentMapper.selectComment(index);
	}
}
