package com.example.demo.model.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RegisterForm {

	private long id;
	private String name;
	@NotEmpty(message = "암호가 공백입니다.")
	private String password;
	private String mail;
}
