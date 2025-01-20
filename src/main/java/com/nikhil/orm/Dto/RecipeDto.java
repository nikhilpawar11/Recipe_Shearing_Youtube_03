package com.nikhil.orm.Dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nikhil.orm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecipeDto {

	private String id;

	private String title;

	private String imageUrl;

	private String description;

	private boolean vegetarian;

	private LocalDateTime createdAt;

	private User user;

}
