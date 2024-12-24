package com.nikhil.orm.services;

import java.util.List;

import com.nikhil.orm.entities.Recipe;
import com.nikhil.orm.entities.User;

public interface RecipeService {
	
	public Recipe createRecipe(Recipe recipe, User user);
	
	public Recipe findRecipeById(int id);
	
	public void deleteRecipe(int id);
	
	public Recipe updateRecipe(Recipe recipe, int id);
	
	public List<Recipe> findAllRecipe();
	
	public Recipe likeRecipe(int id, User user);

}
