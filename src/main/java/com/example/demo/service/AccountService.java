package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDateTime;

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
		String username = account.getUsername();
		String encryPassword = bCryptPasswordEncoder.encode(account.getPassword());
		Account mailAccount = email_send(username);
		accountMapper.insert(username, encryPassword, account.getMail(), 
				mailAccount.getMailSendTime(),mailAccount.getAuthKey());
	}
	public void emailConfirm(String username, String authKey) {
		String originalKey = accountMapper.selectAuthKey(username);
		if(originalKey.equals(authKey)) 
			accountMapper.IsEmailVerified(username);
	}

	public Account email_send(String username) throws MessagingException {
		String authKey = "";
		Account account = accountMapper.selectAccountOneColumn("mail_send_time", username);
		Account mailAccount = new Account();
		LocalDateTime now = LocalDateTime.now();
		if(account == null || account.getMailSendTime().plusHours(1).isAfter(LocalDateTime.now())) {
			authKey = simpleEmailServiceImpl.sendMail("kma952727@gmail.com", username);
			now = LocalDateTime.now();
			accountMapper.updateAccountOneColumn("mail_send_time", now, username);
		}
		mailAccount.setAuthKey(authKey);
		mailAccount.setMailSendTime(now);
		return mailAccount;
	}
}
