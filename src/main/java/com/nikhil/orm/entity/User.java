package com.nikhil.orm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "User_Table")
public class User {
	
	@Column(name = "User_Id")
	private String id;
	
	@Column(name = "User_Name")
	private String name;
	
	@Column(name = "User_Email")
	private String email;
	
	@Column(name = "User_Password")
	private String password;

}
