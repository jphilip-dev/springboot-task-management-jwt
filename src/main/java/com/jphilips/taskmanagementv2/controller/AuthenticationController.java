package com.jphilips.taskmanagementv2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.taskmanagementv2.dto.FieldErrorResponse;
import com.jphilips.taskmanagementv2.dto.LoginRequest;
import com.jphilips.taskmanagementv2.dto.MyUserRequest;
import com.jphilips.taskmanagementv2.services.AuthenticationService;
import com.jphilips.taskmanagementv2.services.JWTService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@Valid
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/register")
	public ResponseEntity<FieldErrorResponse> register(@Valid @RequestBody MyUserRequest myUserRequest,
			BindingResult bindingResult) {

		FieldErrorResponse errorResponse = new FieldErrorResponse();

		// check first the username availability and append error to bindingResult if
		// any
		authService.validateUsernameAvailability(myUserRequest.getUsername(), bindingResult);

		if (bindingResult.hasErrors()) {
			for (FieldError err : bindingResult.getFieldErrors()) {
				errorResponse.addError(err.getField(), err.getDefaultMessage());
			}
		}

		HttpStatus status;

		if (errorResponse.getErrors().isEmpty()) {
			status = HttpStatus.CREATED;
			authService.registerUser(myUserRequest);
		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<>(errorResponse, status);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest,
			BindingResult bindingResult) {
		HttpStatus status;
		FieldErrorResponse errorResponse = new FieldErrorResponse();
		
		if (bindingResult.hasErrors()) {
			for (FieldError err : bindingResult.getFieldErrors()) {
				errorResponse.addError(err.getField(), err.getDefaultMessage());
			}
		}
		
		try {
			
			@SuppressWarnings("unused")
			Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	    			loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (BadCredentialsException e) {
			errorResponse.addError("login", "Check your Username & Password");
		} catch (Exception e) {
			errorResponse.addError("login", "Contact your Administrator");
		}
		
		
		if (errorResponse.getErrors().isEmpty()) {
			status = HttpStatus.ACCEPTED;
			
			// getToken
			String token = jwtService.generateToken(loginRequest.getUsername());
			
			return new ResponseEntity<Object>(token,status);
		} else {
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(errorResponse, status);
		}
		
	}

}
