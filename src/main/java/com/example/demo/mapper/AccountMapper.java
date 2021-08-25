package com.example.demo.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
@Mapper
public interface AccountMapper {

	public Account selectById(int id);
	public Account selectByName(String username);
	public String selectPasswordByName(String username);
	public void insert(String username, String password, String mail, String authKey);
	public void insertAuthKey(String authKey, String username);
	public String getAuthKey(String username);
	public void IsEmailVerified(String username);
}
