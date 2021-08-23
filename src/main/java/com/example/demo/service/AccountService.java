package com.example.demo.service;

import javax.mail.MessagingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.mail.SimpleEmailServiceImpl;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class AccountService {

	@Autowired private AccountMapper accountMapper;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired private SimpleEmailServiceImpl simpleEmailServiceImpl;
	
	public Account getAccountById(int id) {
		return accountMapper.selectById(id);
	}
	public Account getAccountByName(String name) {
		return accountMapper.selectByName(name);
	}
	public void register(Account account) throws MessagingException {
		String encryPassword = bCryptPasswordEncoder.encode(account.getPassword());
		String authKey = simpleEmailServiceImpl.sendMail("kma952727@gmail.com");
		String useranme = account.getUsername();
		accountMapper.insert(useranme, encryPassword, account.getMail(), authKey);
	}

}
