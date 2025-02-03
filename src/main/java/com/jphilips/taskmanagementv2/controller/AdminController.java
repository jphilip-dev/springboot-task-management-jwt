package com.jphilips.taskmanagementv2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.taskmanagementv2.dto.MyUserResponse;
import com.jphilips.taskmanagementv2.services.MyUserService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private MyUserService myUserService;
	
	@GetMapping("/users")
	public List<MyUserResponse> getAllUsers(){
		return myUserService.getAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public MyUserResponse getUserById(@PathVariable Long id){
		return myUserService.getUserById(id);
	}
	
	@PutMapping("/users/{id}/roles")
	public MyUserResponse updateUserRoles(@PathVariable Long id, @RequestParam String[] value) throws Exception {
	
		return myUserService.setRole(id, value);
		
	}
	
	@PutMapping("/users/{id}/status")
	public MyUserResponse updateUserStatus(@PathVariable Long id, @RequestParam boolean value) {
		return myUserService.setStatus(id, value);
		
	}
	@PutMapping("/users/{id}/reset-password")
	public MyUserResponse resetUserPassword(@PathVariable Long id, @RequestParam String value) {
		return myUserService.resetPassword(id, value);
		
	}
}
