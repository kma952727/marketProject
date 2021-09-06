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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Data
@ToString @NoArgsConstructor
public class Account implements Serializable{
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIsEmailverified() {
		return isEmailverified;
	}

	public void setIsEmailverified(Boolean isEmailverified) {
		this.isEmailverified = isEmailverified;
	}

	public LocalDateTime getMailSendTime() {
		return mailSendTime;
	}

	public void setMailSendTime(LocalDateTime mailSendTime) {
		this.mailSendTime = mailSendTime;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	
	private Long accountId;
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
		private Long accountId;
		
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
		public Builder setAccountId(Long val) {
			accountId = val;
			return this;
		}
		public Account build() {
			Account account = 
					new Account(name, password, mail, enabled, accountId);
			return account;
		}	
	}
	
	private Account(String name, String password,
			String mail, Boolean enabled, Long accountId) {
		this.username = name;
		this.password = password;
		this.mail = mail;
		this.enabled = enabled;
		this.accountId = accountId;
	}
	
}
