package com.example.demo.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RegisterForm {

	private long id;
	private String name;
	private String password;
	private String mail;
}
