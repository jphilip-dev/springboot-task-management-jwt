package com.jphilips.taskmanagementv2.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskResponse {
	private Long id;

	private String description;

	private String assignedTo;

	private String assignedBy;

	private LocalDate assignedDate;

	private String status;
	
	private int priority;

	private LocalDate completionDate;

	private String completionNotes;
}
