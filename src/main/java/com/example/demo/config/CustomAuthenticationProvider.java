package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired private CustomUserDetailsService userDetailsService;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();
		log.info("provider진입"+username+"/"+password);
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if(user == null) {
			log.info("잘못된 아이디!");
			throw new BadCredentialsException("잘못된 정보입니다.");
		}
		if(!passwordEncoder.matches(password, user.getPassword())) {
			log.info("잘못된 패스워드!");
			throw new BadCredentialsException("잘못된 패스워드입니다.");
		}
		return new CustomAuthenticationToken(username, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
