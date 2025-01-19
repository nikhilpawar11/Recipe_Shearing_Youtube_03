package com.nikhil.orm.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikhil.orm.Dto.RecipeDto;
import com.nikhil.orm.Dto.UserDto;
import com.nikhil.orm.entity.Recipe;
import com.nikhil.orm.entity.User;
import com.nikhil.orm.exception.ResourceNotFoundException;
import com.nikhil.orm.peginationhelper.Helper;
import com.nikhil.orm.peginationhelper.PegiableResponse;
import com.nikhil.orm.repository.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	
	@Autowired
	private RecipeRepository recipeRepo;
	
	@Autowired
	private ModelMapper mapper;
	

	@Override
	public RecipeDto createRecipe(RecipeDto recipeDto, UserDto userDto) {
		
		User user = mapper.map(userDto, User.class);
		
		Recipe recipe = mapper.map(recipeDto, Recipe.class);
		
		recipe.setId(UUID.randomUUID().toString());
		recipe.setTitle(recipeDto.getTitle());
		recipe.setDescription(recipeDto.getDescription());
		recipe.setImageUrl(recipeDto.getImageUrl());
		recipe.setCreatedAt(LocalDateTime.now());
		recipe.setUser(user);
		
		Recipe createRecipe = recipeRepo.save(recipe);
		
		return mapper.map(createRecipe, RecipeDto.class);
	}

	@Override
	public RecipeDto updateRecipe(RecipeDto recipeDto, String recipeId) {
		
		Recipe recipe = recipeRepo.findById(recipeId).orElseThrow(() -> new ResourceNotFoundException("Recipe not found with given recipe id "+recipeId));
		
		recipe.setTitle(recipeDto.getTitle());
		recipe.setDescription(recipeDto.getDescription());
		recipe.setImageUrl(recipeDto.getImageUrl());
		
		Recipe updateRecipe = recipeRepo.save(recipe);
		
		return mapper.map(updateRecipe, RecipeDto.class);
	}

	@Override
	public void deleteRecipe(String recipeId) {
		
		Recipe recipe = recipeRepo.findById(recipeId).orElseThrow(() -> new ResourceNotFoundException("Recipe not found with given recipe id "+recipeId));
		
		recipeRepo.delete(recipe);
		
	}

	@Override
	public RecipeDto getRecipeById(String recipeId) {
		
		Recipe recipe = recipeRepo.findById(recipeId).orElseThrow(() -> new ResourceNotFoundException("Recipe not found with given recipe id "+recipeId));
		
		return mapper.map(recipe, RecipeDto.class);
	}

	@Override
	public List<RecipeDto> getAllRecipe() {
		
		List<Recipe> allRecipe = recipeRepo.findAll();
		
		List<RecipeDto> recipeDto = allRecipe.stream().map(ex -> mapper.map(ex, RecipeDto.class)).collect(Collectors.toList());
		
		return recipeDto;
	}

	@Override
	public PegiableResponse<RecipeDto> getRecipeWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Recipe> page = recipeRepo.findAll(pageable);
		
		PegiableResponse<RecipeDto> peginationResponse = Helper.getPeginationResponse(page, RecipeDto.class);
		
		return peginationResponse;
	}

	@Override
	public RecipeDto getRecipeByTitle(String title) {
		
		Recipe recipe = recipeRepo.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Recipe not found with given title "+title));
		
		return mapper.map(recipe, RecipeDto.class);
	}


}
