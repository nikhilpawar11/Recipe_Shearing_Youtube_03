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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.exception.ApiMessageResponse;
import com.nikhil.orm.peginationhelper.PegiableResponse;
import com.nikhil.orm.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		
		UserDto savedUser = userService.createUser(userDto);
		
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		
	}

	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId){
		
		UserDto updateUser = userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiMessageResponse> deleteUser(@PathVariable String userId){
		
		userService.deleteUser(userId);
		
		ApiMessageResponse apiMessageResponse = ApiMessageResponse.builder().message("User deleted successfuly "+userId).success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/userById/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		
		UserDto userById = userService.getUserById(userId);
		
		return new ResponseEntity<>(userById, HttpStatus.OK);
		
	}
	
	@GetMapping("/allUser")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		List<UserDto> allUser = userService.getAllUser();
		
		return new ResponseEntity<>(allUser, HttpStatus.OK);
		
	}
	
	@GetMapping("/getUserWithPegination")
	public ResponseEntity<PegiableResponse<UserDto>> getAllUserWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<UserDto> userWithPegination = userService.getUserWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(userWithPegination, HttpStatus.OK);
		
	}
	
}
