package com.example.demo.config.mail;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MailKeyGenerator {

	private boolean lowerCheck;
	private int size;
	
	public String getKey(int size, boolean lowerCheck) {
		this.size = size;
		this.lowerCheck = lowerCheck;
		return init();
	}
	
	private String init() {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int num = 0;
		do {
			num = random.nextInt(75) + 48;
			if((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
				sb.append((char)num);
			} else {
				continue;
			}
			
		}while(sb.length() < size);
		if(lowerCheck) {
			return sb.toString().toLowerCase();
		}
		return sb.toString();
	}
}
