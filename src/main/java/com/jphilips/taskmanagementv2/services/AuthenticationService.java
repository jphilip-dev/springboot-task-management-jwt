package com.jphilips.taskmanagementv2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.jphilips.taskmanagementv2.dto.MyUserRequest;
import com.jphilips.taskmanagementv2.dto.mappers.MyUserMapper;
import com.jphilips.taskmanagementv2.entity.MyUser;
import com.jphilips.taskmanagementv2.repositories.MyUserRepository;


@Service
public class AuthenticationService {

	@Autowired
	private MyUserRepository myUserRepository;

	public void registerUser(MyUserRequest myUserRequest) {
		
		MyUser newUser = MyUserMapper.toEntity(myUserRequest);
		
		// Newly registered users are set to Active false
		newUser.setActive(true);
		
		// ALL USERS are set to USER role first
		newUser.setRole("MEMBER");
		
		myUserRepository.save(newUser);

	}

	// Helper methods
	
	public boolean isUsernameAvailable(String username) {
		MyUser user = myUserRepository.findByUsername(username).orElse(null);
		
		if (user == null) 
			return true;
		else
			return false;
	}

	public void validateUsernameAvailability(String username, BindingResult bindingResult) {
		if (username != null) {

			if (!isUsernameAvailable(username)) {
				bindingResult.rejectValue("username", "username.alreadyTaken", "Username is already taken");
			}
		}

	}
}
