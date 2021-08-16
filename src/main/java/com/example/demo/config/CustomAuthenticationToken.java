package com.example.demo.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken{

	private String username;
	private String credential;
	
	public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

	
	public CustomAuthenticationToken(String username, String credential, 
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.username = username;
		this.credential = credential;
	}

	@Override
	public Object getCredentials() {
		return this.credential;
	}

	@Override
	public Object getPrincipal() {
		return this.username;
	}

}
