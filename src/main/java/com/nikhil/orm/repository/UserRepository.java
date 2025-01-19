package com.nikhil.orm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.orm.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	// find user by email
	
	Optional<User> findByEmail(String email);

}
