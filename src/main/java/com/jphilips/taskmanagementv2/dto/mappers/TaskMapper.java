package com.jphilips.taskmanagementv2.dto.mappers;

import com.jphilips.taskmanagementv2.dto.TaskRequest;
import com.jphilips.taskmanagementv2.dto.TaskResponse;
import com.jphilips.taskmanagementv2.entity.Task;

public class TaskMapper {
	
	public static TaskResponse toDto(Task task) {
		return new TaskResponse(
				task.getId(),
				task.getDescription(),
				task.getAssignedTo().getUsername(),
				task.getAssignedBy().getUsername(),
				task.getAssignedDate(),
				task.getStatus(),
				task.getPriority(),
				task.getCompletionDate(),
				task.getCompletionNotes()			
				);
	}
	
	public static Task toEntity(TaskRequest taskRequest) {
		Task task = new Task();
		
		task.setDescription(taskRequest.getDescription());
		// Assigned to and by will be set in the service
		// Assigned Date will be set in the Service
		task.setStatus(taskRequest.getStatus());
		task.setPriority(taskRequest.getPriority());
		task.setCompletionDate(taskRequest.getCompletionDate());
		task.setCompletionNotes(taskRequest.getCompletionNotes());
		
		
		return task;
	}
}
