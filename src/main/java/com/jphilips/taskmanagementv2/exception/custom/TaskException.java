package com.jphilips.taskmanagementv2.exception.custom;

public class TaskException extends RuntimeException{

	private static final long serialVersionUID = -1841220834977664300L;
	
	public TaskException() {
		super();
	}
	public TaskException(String message) {
		super(message);
	}
	
	public TaskException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public TaskException(Throwable cause) {
        super(cause);
    }

}
