package com.jphilips.taskmanagementv2.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jphilips.taskmanagementv2.dto.MyUserResponse;
import com.jphilips.taskmanagementv2.dto.mappers.MyUserMapper;
import com.jphilips.taskmanagementv2.entity.MyUser;
import com.jphilips.taskmanagementv2.entity.UserRole;
import com.jphilips.taskmanagementv2.exception.custom.UserException;
import com.jphilips.taskmanagementv2.repositories.MyUserRepository;
import com.jphilips.taskmanagementv2.repositories.UserRoleRepository;

@Service
public class MyUserService implements UserDetailsService {

	private MyUserRepository myUserRepository;
	
	private UserRoleRepository userRoleRepository;
	
	public MyUserService(MyUserRepository myUserRepository,UserRoleRepository userRoleRepository) {
		this.myUserRepository = myUserRepository;
		this.userRoleRepository = userRoleRepository;
	}

	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public List<MyUserResponse> getAllUsers() {
		return myUserRepository.findAll().stream().map(myUser -> MyUserMapper.toDto(myUser))
				.collect(Collectors.toList());
	}

	public MyUserResponse getUserById(Long id) {
		return MyUserMapper.toDto(findUserById(id));
	}

	public MyUserResponse setRole(Long id, String... roles) throws Exception {
		
		if (roles.length == 0) {
			throw new Exception("Empty Roles");
		}
		
		for(String s : roles) {
			if (!s.equals("LEAD") && !s.equals("MEMBER")) {
				throw new Exception("Invalid role: " + s); // Controller advice will catch this and return internal server error
			}
		}

		MyUser myUser = findUserById(id);
		List<UserRole> currentRoles = myUser.getRoles();
		
		myUser.setRoles(null);
		
		for (var role : currentRoles) {

			userRoleRepository.delete(role);

		}
		
		myUser.setRole(roles);

		myUser = myUserRepository.save(myUser);

		return MyUserMapper.toDto(myUser);

	}

	public MyUserResponse setStatus(Long id, boolean status) {

		MyUser myUser = findUserById(id);

		myUser.setActive(status);

		myUser = myUserRepository.save(myUser);

		return MyUserMapper.toDto(myUser);

	}

	public MyUserResponse resetPassword(long id, String newPassword) {
		MyUser myUser = findUserById(id);

		myUser.setPassword(passwordEncoder.encode(newPassword));

		myUser = myUserRepository.save(myUser);

		return MyUserMapper.toDto(myUser);
	}

	// Helper method
	private MyUser findUserById(Long id) {

		MyUser myUser = myUserRepository.findById(id).orElseThrow(() -> new UserException("ID %s not found"));

		return myUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = myUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " username do not exist"));

		return User.builder().username(username).password(user.getPassword()).roles(extractRoles(user.getRoles()))
				.disabled(!user.isActive()).build();

	}

	private String[] extractRoles(List<UserRole> roles) {
		return roles.stream().map(r -> r.getRole()).toArray(String[]::new);

	}

}
