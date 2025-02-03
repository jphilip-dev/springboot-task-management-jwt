package com.jphilips.taskmanagementv2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.taskmanagementv2.dto.TaskRequest;
import com.jphilips.taskmanagementv2.dto.TaskResponse;
import com.jphilips.taskmanagementv2.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
@Valid
public class TasksController {
	
	private TaskService taskService;
	
	public TasksController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/my-tasks")
	public List<TaskResponse> getAllMyTasks(Authentication authentication, @RequestParam(required = false) List<String> statuses) {
		
		return taskService.getAllMyTasks(authentication, statuses);
	}
	
	@GetMapping("/delegated-tasks")
	public List<TaskResponse> getAllDelegatedTasks(Authentication authentication, @RequestParam(required = false) List<String> statuses) {
		return taskService.getAllDelegatedTasks(authentication,statuses);
	}
	
	@GetMapping("/{taskId}")
	public TaskResponse getTask(Authentication authentication, @PathVariable Long taskId) {
		return taskService.getTaskById(authentication, taskId);
	}
	
	@PostMapping()
	public ResponseEntity<Void> addTask(Authentication authentication, @Valid @RequestBody TaskRequest taskRequest) {
		
		// Field validation handled by Controller advice see MethodArgumentNotValidException 
		
		taskService.addTask(authentication, taskRequest);  // if exception occurred, response will also be handled by Controller advice
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<Void> updateTask(Authentication authentication, @PathVariable Long taskId,@Valid @RequestBody TaskRequest taskRequest) {
		
		taskService.updateTask(authentication, taskId, taskRequest);  
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void>  deleteTask(Authentication authentication, @PathVariable Long taskId) {
		
		taskService.deleteTask(authentication, taskId); 
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
