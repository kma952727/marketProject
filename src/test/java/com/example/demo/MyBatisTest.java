package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
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
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@SpringBootTest
public class MyBatisTest {

	private static final String NAME = "runa";
	
	@Autowired AccountService accountService;
	
	@DisplayName("insert + select")
	@Test
	public void isConnected_Mapper_interface() {
		Account account = new Account();
		account.setName(NAME);
		account.setPassword("123");
		account.setMail("2323@2424.com");
		
		accountService.insertAccount(account);
		
		Account getAccount = accountService.getAccountByName(NAME);
		assertThat(getAccount.getName()).isEqualTo(NAME);
		
	}
}
