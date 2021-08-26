package com.example.demo.config.validate;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.form.RegisterForm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class RegisterValidator implements Validator{

	private final AccountMapper accountMapper;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(RegisterForm.class);
			
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterForm form = (RegisterForm)target;
		String name = form.getName();
		String password = form.getPassword();
		String mail = form.getMail();

		if(accountMapper.existsRegisterInfo("username", name) != 0 ) {
			errors.rejectValue("name","wrong.nickname", "존재하는 아이디입니다.");
		}
		if(accountMapper.existsRegisterInfo("mail", mail) != 0 ){
			errors.rejectValue("mail","wrong.mail", "존재하는 메일입니다.");
		}
		
	}

}
