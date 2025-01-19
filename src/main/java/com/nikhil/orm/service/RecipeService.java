package com.nikhil.orm.service;

import java.util.List;

import com.nikhil.orm.Dto.RecipeDto;
import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.peginationhelper.PegiableResponse;

public interface RecipeService {
	
	// create recipe
	public RecipeDto createRecipe(RecipeDto recipeDto, UserDto userDto);
	
	// update recipe
	public RecipeDto updateRecipe(RecipeDto recipeDto, String recipeId);
	
	// delete recipe
	public void deleteRecipe(String recipeId);
	
	// get recipe by id
	public RecipeDto getRecipeById(String recipeId);
	
	// get all recipe
	public List<RecipeDto> getAllRecipe();
	
	// get recipe with pagination
	public PegiableResponse<RecipeDto> getRecipeWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);

	// find recipe by title
	public RecipeDto getRecipeByTitle(String title);
	
	
}
