package com.human.project.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncodeUtil {
	
	// 비밀번호 암호화 방식을 bcrypt 로 지정
	@Bean					
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
