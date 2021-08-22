package com.example.demo.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;

@Service
@Transactional
public class AccountService {

	@Autowired private AccountMapper accountMapper;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Account getAccountById(int id) {
		return accountMapper.selectById(id);
	}
	public Account getAccountByName(String name) {
		return accountMapper.selectByName(name);
	}
	public void register(Account account) {
		String encryPassword = bCryptPasswordEncoder.encode(account.getPassword());
		accountMapper.insert(account.getUsername(), encryPassword, account.getMail());
	}
//	public boolean signin(SigninForm signinForm) {
//		String encodedPassword = accountMapper.selectPasswordByName(signinForm.getName());
//		boolean passwordCheck = bCryptPasswordEncoder.matches(signinForm.getPassword(), encodedPassword);
//		if(passwordCheck) 
//			return true;
//		else
//			return false;
//	}
}
