package com.jphilips.taskmanagementv2.exception.custom;

public class TaskNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -4835961377957694223L;

	public TaskNotFoundException() {
		super();
	}
	public TaskNotFoundException(Long TaskId ) {
		super(String.valueOf(TaskId));
	}
	public TaskNotFoundException(String message ) {
        super(message);
    }
	
	public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public TaskNotFoundException(Throwable cause) {
        super(cause);
    }
}
