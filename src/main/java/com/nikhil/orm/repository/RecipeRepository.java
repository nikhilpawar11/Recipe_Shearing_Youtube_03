package com.nikhil.orm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.orm.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

	// find by title
	Optional<Recipe> findByTitle(String title);
	
}
