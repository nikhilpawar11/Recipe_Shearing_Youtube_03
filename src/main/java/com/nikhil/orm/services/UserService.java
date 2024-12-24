package com.nikhil.orm.services;

import java.util.List;

import com.nikhil.orm.entities.User;

public interface UserService {
	
	//create user
	public User creteUser(User user);
	
	//get all user
	public List<User> findAllUser();
	
	//get user by id
	public User getUserById(int id);
	
	//delete user
	public void deleteUser(int id);
	
	//update user
	public User updateUser(User user ,int id);
	
	//find user by email
	public User findByEmail(String email);

}
