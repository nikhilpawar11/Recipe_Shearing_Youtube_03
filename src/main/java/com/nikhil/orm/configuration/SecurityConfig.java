package com.nikhil.orm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collections;

@Configuration
public class SecurityConfig {
	
	
	@Bean
	SecurityFilterChain securityFilterChainHttp(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize.requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(CorsConfigurationSource()))
                .formLogin(withDefaults());
		
		return httpSecurity.build();
		
	}
	

	private CorsConfigurationSource CorsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration cfg = new CorsConfiguration();
				
				cfg.setAllowedOrigins(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Collections.singletonList("*"));
				cfg.setMaxAge(3600L);
				
				
				return cfg;
			}
		};
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
