package br.com.bbl.consolultra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
 		http.csrf().disable().authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/erro").permitAll()
			.antMatchers("/welcome").permitAll()
			.antMatchers("/home").permitAll()
			.antMatchers("/participant").permitAll()
			.antMatchers("/pdf/**").permitAll()
			.antMatchers("/about").permitAll()
			.antMatchers("/articles").permitAll()
			.antMatchers("/partForm").permitAll()
			.antMatchers("/term").permitAll()
			.antMatchers("/init").permitAll()
			.antMatchers("/next").permitAll()
			.antMatchers("/answering").permitAll()
			//.antMatchers("/userForm").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/welcome");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/bootstrap/**", "/css/**", "/img/**", "/materialize/**");
	}
}
