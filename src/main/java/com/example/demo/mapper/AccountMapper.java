package com.example.demo.mapper;

import java.time.LocalDateTime;

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
	public void insert(String username, String password, String mail, LocalDateTime mailSendTime, String authKey);
	public void insertAuthKey(String authKey, String username);
	public String selectAuthKey(String username);
	public void IsEmailVerified(String username);
	public Account selectAccountOneColumn(String column, String username);
	public void updateAccountOneColumn(String column, LocalDateTime value,
			String username);
	public Integer existsRegisterInfo(String column, String value);
}
