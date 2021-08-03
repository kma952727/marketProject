package com.example.demo.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;

@Service
@Transactional
public class AccountService {

	@Autowired private AccountMapper accountMapper;
	
	public Account getAccountById(int id) {
		return accountMapper.selectById(id);
	}
	public Account getAccountByName(String name) {
		return accountMapper.selectByName(name);
	}
	public void insertAccount(Account account) {
		accountMapper.insert(account.getName(), account.getPassword(), account.getMail());
	}
	public void register(Account account) {
		accountMapper.insert(account.getName(), 
				account.getPassword(), 
				account.getMail());
	}
}
