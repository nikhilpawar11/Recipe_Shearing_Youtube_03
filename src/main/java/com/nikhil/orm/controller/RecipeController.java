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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.orm.Dto.RecipeDto;
import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.exception.ApiMessageResponse;
import com.nikhil.orm.peginationhelper.PegiableResponse;
import com.nikhil.orm.service.RecipeService;
import com.nikhil.orm.service.UserService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/createRecipe/user/{userId}")
	public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto, @PathVariable String userId){
		
		UserDto user = userService.getUserById(userId);
		
		RecipeDto createRecipe = recipeService.createRecipe(recipeDto, user);
		
		return new ResponseEntity<>(createRecipe, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateRecipe/{recipeId}")
	public ResponseEntity<RecipeDto> updateRecipe(@RequestBody RecipeDto recipeDto, @PathVariable int recipeId){
		
		RecipeDto recipe = recipeService.updateRecipe(recipeDto, recipeId);
		
		return new ResponseEntity<>(recipe, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteRecipe/{recipeId}")
	public ResponseEntity<ApiMessageResponse> deleteRecipe(@PathVariable int recipeId){
		
		recipeService.deleteRecipe(recipeId);
		
		ApiMessageResponse apiMessageResponse = ApiMessageResponse.builder().message("Recipe deleted successfully "+recipeId).success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiMessageResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/getRecipeById/{recipeId}")
	public ResponseEntity<RecipeDto> getRecipeById(@PathVariable int recipeId){
		
		RecipeDto recipe = recipeService.getRecipeById(recipeId);
		
		return new ResponseEntity<>(recipe, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllRecipe")
	public ResponseEntity<List<RecipeDto>> getAllRecipe(){
		
		List<RecipeDto> recipe = recipeService.getAllRecipe();
		
		return new ResponseEntity<>(recipe, HttpStatus.OK);
		
	}
	
	@GetMapping("/getRecipeWithPegination")
	public ResponseEntity<PegiableResponse<RecipeDto>> getRecipeWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<RecipeDto> recipeWithPegination = recipeService.getRecipeWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(recipeWithPegination, HttpStatus.OK);
		
	}
	
	
}
