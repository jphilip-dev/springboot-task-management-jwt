package com.jphilips.taskmanagementv2.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskRequest {
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String assignedTo;
	
	@NotBlank
	private String assignedBy;

	@NotBlank
	private String status;
	
	@Min(1)
	@Max(5)
	private Integer priority;

	private LocalDate completionDate;

	private String completionNotes;
}
