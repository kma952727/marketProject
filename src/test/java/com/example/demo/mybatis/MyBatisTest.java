package com.example.demo.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.Account;
import com.example.demo.model.Board;
import com.example.demo.model.page.Criteria;
import com.example.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
public class MyBatisTest {

	private static final String NAME = "runa";
	
	@Autowired AccountService accountService;
	@Autowired AccountMapper accountMapper;
	@Autowired BoardMapper boardMapper;
	
	@Disabled
	@DisplayName("insert + select")
	@Test
	public void isConnected_Mapper_interface() throws MessagingException {
		Account account = new Account();
		account.setUsername(NAME);
		account.setPassword("123");
		account.setMail("2323@2424.com");
		
		accountService.register(account);
		
		Account getAccount = accountService.getAccountByName(NAME);
		assertThat(getAccount.getUsername()).isEqualTo(NAME);
	}
	@Disabled
	@DisplayName("쿼리가 한번만 나가는가?")
	@Test
	public void isCached() throws MessagingException {
		Account account = new Account();
		account.setUsername(NAME);
		account.setPassword("123");
		account.setMail("2323@2424.com");
		
		accountService.register(account);
		
		accountMapper.selectByName(NAME);
		accountMapper.selectByName(NAME);
		accountMapper.selectByName(NAME);
		accountMapper.selectByName(NAME);
		accountMapper.selectByName(NAME);
	}
	@DisplayName("페이징처리 테스트 ( n의 값부터 m의 양이 올바르게 불러지는가?)")
	@Test
	public void isPaging() {
		Criteria criteria = new Criteria();
		List<Board> list = boardMapper.testPaging(criteria);
		list.forEach(board -> log.info("보드값"+board.toString()));
	}
}
