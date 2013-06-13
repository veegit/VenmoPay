package com.vee.venmo;

public class UserException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException() {
		super("User already exists!");
	}

}
