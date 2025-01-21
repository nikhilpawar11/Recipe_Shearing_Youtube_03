package com.nikhil.orm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.config.UserDetailService;
import com.nikhil.orm.entity.JwtRequest;
import com.nikhil.orm.entity.JwtResponse;
import com.nikhil.orm.jwtconfig.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtHelper jwtHelper;

	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest){
		
		doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getEmail());
		
		String token = jwtHelper.generateToken(userDetails);
		
		JwtResponse jwtResponse = JwtResponse.builder().jwtToken(token).message("Login Successfully..!").build();
		
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		
	}


	private void doAuthenticate(String email, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	
	

}
