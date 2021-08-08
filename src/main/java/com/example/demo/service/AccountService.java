package com.example.demo.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.SHA256Util;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;

@Service
@Transactional
public class AccountService {

	@Autowired private AccountMapper accountMapper;
	@Autowired private SHA256Util sha256Util;
	
	public Account getAccountById(int id) {
		return accountMapper.selectById(id);
	}
	public Account getAccountByName(String name) {
		return accountMapper.selectByName(name);
	}
	public void register(Account account) {
		String encryPassword = sha256Util.encrypt(account.getPassword());
		accountMapper.insert(account.getName(), encryPassword, account.getMail());
	}
}
