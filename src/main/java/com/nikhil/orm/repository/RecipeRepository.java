package com.nikhil.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.orm.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
