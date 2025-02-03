package com.jphilips.taskmanagementv2.dto.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jphilips.taskmanagementv2.dto.MyUserRequest;
import com.jphilips.taskmanagementv2.dto.MyUserResponse;
import com.jphilips.taskmanagementv2.entity.MyUser;
import com.jphilips.taskmanagementv2.entity.UserRole;

public class MyUserMapper {
	
	private static BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
	
	// Map entity to DTO
    public static MyUserResponse toDto(MyUser user) {
        return new MyUserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.isActive(),
                extractRoles(user.getRoles())
        );
    }
    

    // Map DTO to entity (useful for creating/updating entities)
    public static MyUser toEntity(MyUserRequest request) {
        MyUser user = new MyUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()) );
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        
        // status ('Active' field) and Roles will be handled in the service layer
        
        return user;
    }

    // Extract roles into a list of strings
    private static List<String> extractRoles(List<UserRole> userRoles) {
    	
    	if (userRoles == null) return new ArrayList<String>();
    	
        return userRoles.stream()
                        .map(UserRole::getRole)
                        .collect(Collectors.toList());
    }
}
