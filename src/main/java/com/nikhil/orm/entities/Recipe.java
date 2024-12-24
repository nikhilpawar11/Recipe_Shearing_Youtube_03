
package com.nikhil.orm.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Recipe_Table")
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String image;
	
	private String description;
	
	private boolean vageterian;
	
	private LocalDateTime createdAt;
	
	private List<Integer> likes = new ArrayList<>();
	
	//this is use because which user us created this recipe
	@ManyToOne   //this is relationship between recipe & user i.e. multiple recipe created by only one user
	private User user;
	

}
