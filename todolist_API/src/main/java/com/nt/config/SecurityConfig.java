package com.nt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.filter.JWTFilter;

@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JWTFilter jwtFilter;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2A, 10);
	}
	
//	@Bean
//	public MappingJackson2HttpMessageConverter jsonConverter() {
//	    return new MappingJackson2HttpMessageConverter();
//	}
//
//	@Bean
//	public ObjectMapper objectMapper() {
//	    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	}
//	//Check Global JSON Configuration: If you are customizing the ObjectMapper, ensure it supports UTF-8 and handles JSON properly. For instance:


	private static final String[] WHITE_LABEL_URLS = { "/user", "/user/login", "/user/verifyRegistration",
			"/user/resendVerificationlink/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		try {
			return http.cors().and().csrf().disable().authorizeHttpRequests().antMatchers(WHITE_LABEL_URLS).permitAll()
					.anyRequest().authenticated()
					.and()
					.addFilterBefore(jwtFilter	, UsernamePasswordAuthenticationFilter.class)
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
		
	}

}