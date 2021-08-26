package com.example.demo.model;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.thymeleaf.standard.expression.Each;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String password;
	private String mail;
	private Boolean enabled;
	private Boolean isEmailverified;
	private LocalDateTime mailSendTime;
	private String authKey;
	private Collection<? extends GrantedAuthority> authorities;

	public static class Builder{
		private String name = "noName";
		private String password = "noPassword";
		private String mail ="noMail";
		private Boolean enabled;
		
		public Builder setName(String val) {
			name = val;
			return this;
		}
		public Builder setPassword(String val) {
			password = val;
			return this;
		}
		public Builder setMail(String val) {
			mail = val;
			return this;
		}
		public Builder setEnabled(Boolean val) {
			enabled = val;
			return this;
		}
		public Account build() {
			Account account = 
					new Account(name, password, mail, enabled);
			return account;
		}	
	}
	
	private Account(String name, String password,
			String mail, Boolean enabled) {
		this.username = name;
		this.password = password;
		this.mail = mail;
		this.enabled = enabled;
	}
	
}
