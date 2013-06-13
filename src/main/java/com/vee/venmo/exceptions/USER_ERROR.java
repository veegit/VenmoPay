package com.vee.venmo.exceptions;

public enum USER_ERROR {
	USER_EXISTS ("This user already exists"),
	USER_DOESNT_EXIST ("No such user")
	;
	
	private final String errorMessage;
	
	private USER_ERROR(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
