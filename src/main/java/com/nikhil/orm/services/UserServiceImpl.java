package com.nikhil.orm.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhil.orm.entities.User;
import com.nikhil.orm.exceptions.ResourceNotFoundException;
import com.nikhil.orm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User creteUser(User user) {

		// create the user
		User createdUser = userRepo.save(user);

		return createdUser;
	}

	@Override
	public List<User> findAllUser() {

		List<User> allUser = userRepo.findAll();

		return allUser;
	}

	@Override
	public User getUserById(int id) {

		User userById = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found by given Id !!"));

		return userById;
	}

	@Override
	public void deleteUser(int id) {

		User deleteUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found by given id !!"));

		userRepo.delete(deleteUser);

	}

	@Override
	public User updateUser(User user, int id) {

		User updateUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found by given id !!"));

		updateUser.setFullName(user.getFullName());
		updateUser.setEmail(user.getEmail());
		updateUser.setPassword(user.getPassword());

		User updatedUser = userRepo.save(updateUser);

		return updatedUser;
	}

	@Override
	public User findByEmail(String email) {

		User userByEmail = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found by given email !!"));

		return userByEmail;
	}

}
