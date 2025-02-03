package com.jphilips.taskmanagementv2.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class MyUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true,nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	
	@Column(columnDefinition = "boolean")
	private boolean active;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserRole> roles;
	

	
	// convenience method
	public void setRole(String... rolesArray) {
		
		roles = new ArrayList<>();
		
		for (String role : rolesArray) {
			UserRole newRole = new UserRole();
			
			newRole.setRole(role);
			newRole.setUser(this);
			
			roles.add(newRole);
		}
		
		
	}
	
	

}
