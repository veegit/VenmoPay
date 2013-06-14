package com.vee.venmo.exceptions;


public class UserException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;
	private USER_ERROR error;
	
	public UserException(USER_ERROR error) {
		super("ERROR: " + error.toString());
		this.error = error;
	}
	
	public USER_ERROR getError() {
		return error;
	}

}
