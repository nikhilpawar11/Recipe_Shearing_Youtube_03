package com.nikhil.orm.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhil.orm.entities.Recipe;
import com.nikhil.orm.entities.User;
import com.nikhil.orm.exceptions.ResourceNotFoundException;
import com.nikhil.orm.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	
	@Autowired
	private RecipeRepository recipeRepo;

	
	@Override
	public Recipe createRecipe(Recipe recipe, User user) {
		
		Recipe createdRecipe = new Recipe();
		createdRecipe.setTitle(recipe.getTitle());
		createdRecipe.setImage(recipe.getImage());
		createdRecipe.setDescription(recipe.getDescription());
		createdRecipe.setUser(user);
		createdRecipe.setCreatedAt(LocalDateTime.now());
		
		Recipe savedRecipe = recipeRepo.save(createdRecipe);
		
		return savedRecipe;
	}

	@Override
	public Recipe findRecipeById(int id) {
		
		Recipe findRecipeById = recipeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found by given id !!"));
		
		return findRecipeById;
	}

	@Override
	public void deleteRecipe(int id) {
		
		Recipe recipeById = recipeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found by given id !!"));
		
		recipeRepo.delete(recipeById);
		
	}

	@Override
	public Recipe updateRecipe(Recipe recipe, int id) {
		
		Recipe recipeById = recipeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found by given id !!"));
		
		recipeById.setTitle(recipe.getTitle());
		recipeById.setImage(recipe.getImage());
		recipeById.setDescription(recipe.getDescription());
		
		Recipe updatedRecipe = recipeRepo.save(recipeById);
				
		return updatedRecipe;
	}

	@Override
	public List<Recipe> findAllRecipe() {
		
		List<Recipe> allRecipe = recipeRepo.findAll();
		
		return allRecipe;
	}

	@Override
	public Recipe likeRecipe(int id, User user) {
		
		Recipe recipe = recipeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found by given id !!"));
		
		if(recipe.getLikes().contains(user.getId())) {
			recipe.getLikes().remove(user.getId());
		} else {
			recipe.getLikes().add(user.getId());
		}
		
		return recipeRepo.save(recipe);
	}

	

}
