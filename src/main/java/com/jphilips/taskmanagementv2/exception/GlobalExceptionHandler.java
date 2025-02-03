package com.jphilips.taskmanagementv2.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jphilips.taskmanagementv2.dto.ExceptionResponse;
import com.jphilips.taskmanagementv2.dto.FieldErrorResponse;
import com.jphilips.taskmanagementv2.exception.custom.UserException;

import io.jsonwebtoken.JwtException;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ExceptionResponse> handleJWTException(JwtException ex){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		
        exceptionResponse.setTimeStamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        exceptionResponse.setError("Unauthorized");
        exceptionResponse.setMessage(ex.getMessage());
        
        
    	return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldErrorResponse errorResponse = new FieldErrorResponse();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addError(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ExceptionResponse> handleUserException(UserException ex){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		
        exceptionResponse.setTimeStamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setError("BAD REQUEST");
        exceptionResponse.setMessage(ex.getMessage());
        
        
    	return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleOtherExceptions(Exception ex){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		
        exceptionResponse.setTimeStamp(LocalDateTime.now());
        exceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exceptionResponse.setError("INTERNAL SERVER ERROR");
        exceptionResponse.setMessage("Please contact your administrator");
        
        System.err.println(ex);
        System.err.println(ex.getMessage());
        
    	return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
