package com.example.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired private PasswordEncoder passwordEncoder;
	
	@Autowired private UserDetailServiceImpl userDetailServiceImpl;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("값+"+authentication.getPrincipal());
		String name = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		CustomUser user = (CustomUser) userDetailServiceImpl.loadUserByUsername(name);
			
		if(!passwordEncoder.matches(password, user.getAccount().getPassword()))
			throw new BadCredentialsException(password);
			
		return new UsernamePasswordAuthenticationToken(user, password, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
