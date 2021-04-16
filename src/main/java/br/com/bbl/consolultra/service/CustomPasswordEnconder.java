package br.com.bbl.consolultra.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPasswordEnconder {
	
	@Bean
	public PasswordEncoder enconder() {
		return new BCryptPasswordEncoder();
	}

}
 