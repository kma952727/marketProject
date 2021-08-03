package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Account {
	
	private Long id;
	private String name;
	private String password;
	private String mail;
	
	public static class Builder{
		private String name = "noName";
		private String password = "noPassword";
		private String mail ="noMail";
		
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
		public Account build() {
			Account account = 
					new Account(name, password, mail);
			return account;
		}	
	}
	
	private Account(String name, String password,
			String mail) {
		this.name = name;
		this.password = password;
		this.mail = mail;
	}
	
}
