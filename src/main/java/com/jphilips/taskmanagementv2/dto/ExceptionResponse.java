package com.jphilips.taskmanagementv2.dto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class ExceptionResponse {
	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
}

