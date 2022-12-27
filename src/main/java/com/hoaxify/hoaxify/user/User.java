package com.hoaxify.hoaxify.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data 
@Entity
//We can use @Table annotation to set custom name for the User table
@Table(name="users")
public class User {
	
	@Id //PK
	@GeneratedValue // Auto Unique Id generation
	private long id; //PK
	private String username;
	private String displayname;
	private String password;
	
}
