package com.example.demo.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails{
	
	 private String username;
	 private String password;
	 private boolean isEnabled;
	 private boolean isAccountNonExpired;
	 private boolean isAccountNonLocked;
	 private boolean isCredentialsNonExpired;
	 private Collection<? extends GrantedAuthority> authorities;


}
