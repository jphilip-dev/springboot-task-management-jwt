package com.jphilips.taskmanagementv2.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class FieldErrorResponse {
	
	private Map<String, String> errors = new HashMap<String, String>();;
	
	public void addError(String key, String error) {
	
		errors.put(key, error);
	}
}
