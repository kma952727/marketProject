package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
@Mapper
public interface AccountMapper {

	public Account selectById(int id);
	public Account selectByName(String name);
	public String selectPasswordByName(String name);
	public void insert(String name, String password, String mail);
}
