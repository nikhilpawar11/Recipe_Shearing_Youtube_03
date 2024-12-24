package com.nikhil.orm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.entities.Recipe;
import com.nikhil.orm.entities.User;
import com.nikhil.orm.exceptions.ApiResponseMessage;
import com.nikhil.orm.services.RecipeService;
import com.nikhil.orm.services.UserService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/createRecipe/user/{id}")
	public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe, @PathVariable int id){
		
		User userById = userService.getUserById(id);
		
		Recipe createdRecipe = recipeService.createRecipe(recipe, userById);
		
		return new ResponseEntity<>(createdRecipe, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllRecipe")
	public ResponseEntity<List<Recipe>> getAllRecipe(){
		
		List<Recipe> allRecipe = recipeService.findAllRecipe();
		
		return new ResponseEntity<>(allRecipe, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteRecipe/recipe/{id}")
	public ResponseEntity<ApiResponseMessage> deleteRecipe(@PathVariable int id){
		
		ApiResponseMessage responseMessage = ApiResponseMessage.builder()
				.message("Recipe deleted successfully !!")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		
		recipeService.deleteRecipe(id);
		
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		
	}
	
	@PutMapping("/updateRecipe/recipe/{id}")
	public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe, @PathVariable int id){
		
		Recipe updatedRecipe = recipeService.updateRecipe(recipe, id);
		
		return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
		
	}
	
	@PutMapping("/likeRecipe/{id}/user/{userId}")
	public ResponseEntity<Recipe> likeRecipe(@PathVariable int id, @PathVariable int userId){
		
		User userById = userService.getUserById(userId);
		
		Recipe likeRecipe = recipeService.likeRecipe(id, userById);
				
     	return new ResponseEntity<>(likeRecipe, HttpStatus.OK);
		
	}
	
}