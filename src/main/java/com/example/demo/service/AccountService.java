package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.mail.SimpleEmailServiceImpl;
import com.example.demo.file.FileUtils;
import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.form.RegisterForm;
import com.example.demo.model.form.SigninForm;

import lombok.extern.slf4j.Slf4j;
/**
 * 회원과 이메일관련기능을 
 * 처리하는 클래스입니다.
 * 
 * @author cat95
 */
@Slf4j
@Service
@Transactional
public class AccountService {
	/**
	 * 개발중 인증메일은 개발자에게 발송되도록 했습니다.
	 */
	private final static String MAIL = "kma952727@gmail.com";
	
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
		//'emailSend'에서 인증메일을 발송후 인증키값과 현재시간을 가지고옵니다. 
		Account mailAccount = emailSend(username);
		accountMapper.insert(username, encryPassword, account.getMail(), 
				mailAccount.getMailSendTime(),mailAccount.getAuthKey());
	}
	
	/**
	 * 가입회원에게 전송된 메일인증링크를 
	 * 클릭하면 해당함수가 호출됩니다.
	 * 그리고 db에 있는 인증값과 비교합니다.
	 * 
	 * @param username 인증메일에 포함된 유저이름
	 * @param authKey 인증메일에 포함된 인증키
	 */
	public void emailConfirm(String username, String authKey) {
		String originalKey = accountMapper.selectAuthKey(username);
		if(originalKey.equals(authKey)) {
			accountMapper.IsEmailVerified(username);
		}
	}
	/**
	 * 로그인 후 해당 유저가 이메일인증이 안되어있을시
	 * 상단 알람창에서 이메일재전송버튼을 누르면 호출되는 함수입니다.
	 * 
	 * @param username 현재 로그인된 유저이름
	 * @return 해당 함수는 가입기능에서도 호출됩니다. 인증키와 발송시간을 담은 모델을 반환합니다.
	 * @throws MessagingException
	 */
	public Account emailSend(String username) throws MessagingException {
		
		// db에서 'username'테이블의 'mail_send_time'의 컬럼에 값이 존재하는지 확인합니다. 존재하면 해당값을 가지고옵니다, 없으면 null이 됩니다.
		Account account = accountMapper.selectAccountOneColumn("mail_send_time", username);
		LocalDateTime now = LocalDateTime.now();
		Account mailAccount = new Account();
		String authKey = "";
		/**
		 *  db에 인증메일을 보낸 이력(전송시간)이 없거나, 전송시간 + 60분이 현재 시각보다 미래이면
		 *  인증메일을 다시 보내고 해당 유저의 메일 전송 시간을 현재 시간으로 초기화합니다.
		 */
		if(account == null || account.getMailSendTime().plusHours(1).isAfter(LocalDateTime.now())) {
			authKey = simpleEmailServiceImpl.sendMail(MAIL, username);
			now = LocalDateTime.now();
			accountMapper.updateAccountOneColumn("mail_send_time", now, username);
		}
		mailAccount.setAuthKey(authKey);
		mailAccount.setMailSendTime(now);
		return mailAccount;
	}
}
