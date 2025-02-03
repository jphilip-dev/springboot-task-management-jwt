package com.jphilips.taskmanagementv2.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyUserResponse {
	
	private long id;
	private String username;

	private String firstName;
	private String lastName;

	private boolean active;

	private List<String> roles;
	
}







