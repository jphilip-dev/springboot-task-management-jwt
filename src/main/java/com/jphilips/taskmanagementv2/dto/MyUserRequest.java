package com.jphilips.taskmanagementv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyUserRequest {
	
	@NotBlank
	private String username;
	
	@NotBlank
	@Size(min = 6, message = "Minimum of 6 characters")
	private String password;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
}
