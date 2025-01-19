package com.nikhil.orm.service;

import java.util.List;

import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.peginationhelper.PegiableResponse;

public interface UserService {
	
	// create user
	public UserDto createUser(UserDto userDto);
	
	// update user
	public UserDto updateUser(UserDto userDto, String userId);
	
	// delete user
	public void deleteUser(String userId);
	
	// get single user
	public UserDto getUserById(String userId);
	
	// get all user
	public List<UserDto> getAllUser();

	// get all user with pagination
	public PegiableResponse<UserDto> getUserWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);
	
}
