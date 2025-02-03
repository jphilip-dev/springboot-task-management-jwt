package com.jphilips.taskmanagementv2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jphilips.taskmanagementv2.dto.TaskRequest;
import com.jphilips.taskmanagementv2.dto.TaskResponse;
import com.jphilips.taskmanagementv2.dto.mappers.TaskMapper;
import com.jphilips.taskmanagementv2.entity.MyUser;
import com.jphilips.taskmanagementv2.entity.Task;
import com.jphilips.taskmanagementv2.exception.custom.TaskNotFoundException;
import com.jphilips.taskmanagementv2.exception.custom.TaskPermissionException;
import com.jphilips.taskmanagementv2.repositories.MyUserRepository;
import com.jphilips.taskmanagementv2.repositories.TaskRepository;

@Service
public class TaskService {

	private TaskRepository taskRepository;

	private MyUserRepository myUserRepository;

	public TaskService(TaskRepository taskRepository, MyUserRepository myUserRepository) {
		this.taskRepository = taskRepository;
		this.myUserRepository = myUserRepository;
	}

	public List<TaskResponse> getAllMyTasks(Authentication authentication, List<String> statuses) {
		String username = authentication.getName();
		
		if (statuses != null && !statuses.isEmpty()) {
			return taskRepository.findByAssignedTo_UsernameAndStatusInOrderByPriorityAscIdAsc(username,statuses).stream()
					.map(task -> TaskMapper.toDto(task))
					.collect(Collectors.toList());
		}
		
		return taskRepository.findByAssignedTo_UsernameOrderByPriorityAscIdAsc(username).stream()
				.map(task -> TaskMapper.toDto(task))
				.collect(Collectors.toList());
	}

	public List<TaskResponse> getAllDelegatedTasks(Authentication authentication, List<String> statuses) {
		String username = authentication.getName();
		
		if (statuses != null && !statuses.isEmpty()) {
			return taskRepository.findByAssignedBy_UsernameAndStatusInOrderByPriorityAscIdAsc(username,statuses).stream()
					.map(task -> TaskMapper.toDto(task))
					.collect(Collectors.toList());
		}
		
		return taskRepository.findByAssignedBy_UsernameOrderByPriorityAscIdAsc(username).stream()
				.map(task -> TaskMapper.toDto(task))
				.collect(Collectors.toList());
	}
	
	public TaskResponse getTaskById(Authentication authentication, Long taskId) {
		Task task = getTaskById(taskId);
		
		// security check
		validateTaskAccess(task, authentication.getName());
		
		return TaskMapper.toDto(task);
	}

	public void addTask(Authentication authentication, TaskRequest taskRequest) {
		Task task = TaskMapper.toEntity(taskRequest);
		
		// security check
		// assigned by should be the logged-in user
		if (!authentication.getName().equals(taskRequest.getAssignedBy())) {
			throw new TaskPermissionException();
		}
		
		// not included in the mapper
		MyUser assignedBy = getMyUserByUsername(authentication.getName());
		MyUser assignedTo = getMyUserByUsername(taskRequest.getAssignedTo());
		
		task.setAssignedBy(assignedBy);
		task.setAssignedTo(assignedTo);
		task.setAssignedDate(LocalDate.now());
		
		
		taskRepository.save(task);
	}
	
	public TaskResponse updateTask(Authentication authentication,Long taskId, TaskRequest taskRequest) {
		Task task = getTaskById(taskId);
		
		// security check
		validateTaskAccess(task, authentication.getName());
		
		// assignedBy and assignedDate can't be change
		
		// check if assignedTo has been changed
		if (!task.getAssignedTo().getUsername().equals(taskRequest.getAssignedTo())) {
			
			
			// Check if the user has the LEAD role
	        boolean hasLeadRole = authentication.getAuthorities()
	                                             .stream()
	                                             .anyMatch(auth -> auth.getAuthority().equals("ROLE_LEAD"));
	        
	        if (!hasLeadRole) {
	            throw new TaskPermissionException();
	        }
			
			MyUser assignedTo = getMyUserByUsername(taskRequest.getAssignedTo());
			task.setAssignedTo(assignedTo);
		}
		
		task.setDescription(taskRequest.getDescription());
		task.setStatus(taskRequest.getStatus());
		task.setCompletionDate(taskRequest.getCompletionDate());
		task.setCompletionNotes(taskRequest.getCompletionNotes());
		
		return TaskMapper.toDto(taskRepository.save(task));
	}
	
	public void deleteTask(Authentication authentication,Long taskId) {
		Task task = getTaskById(taskId);
		
		if (!task.getAssignedBy().getUsername().equals(authentication.getName())) {
			throw new TaskPermissionException();
		}
		
		taskRepository.delete(task);
	}
	
	
	// ****Helper methods****
	
	private Task getTaskById(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(taskId));
	}
	
	private MyUser getMyUserByUsername(String username) {
		return  myUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	private void validateTaskAccess(Task task, String username) {
	    if (!task.getAssignedBy().getUsername().equals(username) 
	        && !task.getAssignedTo().getUsername().equals(username)) {
	        throw new TaskPermissionException();
	    }
	}



}
