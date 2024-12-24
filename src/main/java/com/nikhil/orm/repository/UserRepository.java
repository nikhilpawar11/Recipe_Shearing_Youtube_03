package com.nikhil.orm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.orm.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	//custom query
	public Optional<User> findByEmail(String email);

}
