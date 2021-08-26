package com.example.demo.config.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.form.RegisterForm;

public class RegisterValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.getClass().isAssignableFrom(RegisterForm.class);
			
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
