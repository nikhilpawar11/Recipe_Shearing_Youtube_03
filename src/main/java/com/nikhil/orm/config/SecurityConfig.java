package com.nikhil.orm.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		return daoAuthenticationProvider;
		
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf(ex -> ex.disable());
		
		// cors config
		httpSecurity.cors(cors -> cors.configurationSource(ex -> {
			
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			
			corsConfiguration.setAllowedOrigins(List.of("*"));
			corsConfiguration.setAllowedMethods(List.of("*"));
			corsConfiguration.setAllowCredentials(true);
			corsConfiguration.setAllowedHeaders(List.of("*"));
			corsConfiguration.setMaxAge(3000L);
			
			return corsConfiguration;
			
		}));
		
		httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/user/**").permitAll()
				.requestMatchers("/recipe/**").permitAll()
				.anyRequest().authenticated()
				);
		
		
		httpSecurity.sessionManagement(ex -> ex.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.httpBasic(Customizer.withDefaults());
		httpSecurity.formLogin(Customizer.withDefaults());
		
		return httpSecurity.build();
		
	}
	

}
