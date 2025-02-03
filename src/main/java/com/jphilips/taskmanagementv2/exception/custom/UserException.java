package com.jphilips.taskmanagementv2.exception.custom;

public class UserException extends RuntimeException{

	private static final long serialVersionUID = 1131153760625511545L;
	
	public UserException() {
		super();
	}
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public UserException(Throwable cause) {
        super(cause);
    }
	
}
