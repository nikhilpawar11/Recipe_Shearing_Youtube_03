package com.nikhil.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.entities.User;
import com.nikhil.orm.exceptions.ApiResponseMessage;
import com.nikhil.orm.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/createUser")
	public ResponseEntity<User> createdUser(@RequestBody User user){		
		
		User createUser = userService.creteUser(user);
		
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAllUser(){
		
		List<User> getAllUsers = userService.findAllUser();
		
		return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
		
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable int id){
		
		User getUserById = userService.getUserById(id);
		
		return new ResponseEntity<>(getUserById, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<ApiResponseMessage> deleteUserById(@PathVariable int id){
		
		ApiResponseMessage responseMessage = ApiResponseMessage.builder()
				.message("User id deleted successfully !!")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		
		userService.deleteUser(id);
		
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		
	}
	
	@PutMapping("/updateUserById/{id}")
	public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable int id){
		
		User updatedUser = userService.updateUser(user, id);
		
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		
	}
	
	@GetMapping("/getUserByEmail/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email){
		
		User byEmail = userService.findByEmail(email);
		
		return new ResponseEntity<>(byEmail, HttpStatus.OK);
		
	}

}
