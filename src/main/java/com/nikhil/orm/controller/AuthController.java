package com.nikhil.orm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.configuration.JwtProvider;
import com.nikhil.orm.entities.User;
import com.nikhil.orm.exceptions.ResourceNotFoundException;
import com.nikhil.orm.repository.UserRepository;
import com.nikhil.orm.request.LoginRequest;
import com.nikhil.orm.response.AuthResponse;
import com.nikhil.orm.services.CustomUserDetailService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public String postMethodName(@RequestBody String entity) {
		// TODO: process POST request

		return entity;
	}

	public AuthResponse createUser(@RequestBody User user) {

		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();

		User isExistEmail = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email is allready used with another account"));

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFullName(fullName);

		User savedUser = userRepo.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(token);
		authResponse.setMessage("Signup Success");

		return authResponse;
	}

	@PostMapping("/signin")
	public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest) {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(token);
		authResponse.setMessage("Signin Success");

		return authResponse;
	}

	
	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
		
		if(userDetails == null) {
			throw new BadCredentialsException("User not found..!!");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password..!!");
		}
				
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
