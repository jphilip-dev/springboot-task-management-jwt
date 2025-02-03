package com.jphilips.taskmanagementv2.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private MyUser assignedTo;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private MyUser assignedBy;
	
	@Column( nullable = false)
	private LocalDate assignedDate;
	
	@Column( nullable = false)
	private int priority;
	
	@Column( nullable = false)
	private String status;
	
	
	private LocalDate completionDate;
	
	@Column(columnDefinition = "TEXT")
	private String completionNotes;
}
