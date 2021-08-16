package com.example.demo.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired AccountMapper accountMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountMapper.selectByName(username);
		log.info("유저디테일서비스 진입");
		if(account == null) {
			log.info("no account!!!");
			throw new UsernameNotFoundException("db에 존재하지않습니다.");
		}
		log.info("account존재 "+account.getName()+account.getPassword());
		CustomUserDetails user = new CustomUserDetails();
		user.setUsername(account.getName());
		user.setPassword(account.getPassword());
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setAuthorities(null);
		
		return user;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(String username){
		//List<Authority> authList = 
		return null;
	}

}
