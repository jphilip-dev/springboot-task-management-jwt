package com.jphilips.taskmanagementv2.exception.custom;

public class TaskPermissionException extends RuntimeException{

	private static final long serialVersionUID = -7732971837209973514L;

	public TaskPermissionException() {
		super();
	}
	public TaskPermissionException(Long TaskId ) {
		super(String.valueOf(TaskId));
	}
	public TaskPermissionException(String message ) {
        super(message);
    }
	
	public TaskPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public TaskPermissionException(Throwable cause) {
        super(cause);
    }
}
