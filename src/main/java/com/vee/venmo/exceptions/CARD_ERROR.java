package com.vee.venmo.exceptions;

public enum CARD_ERROR {
	INVALID_CARD_ERROR ("This card is invalid"),
	CARD_SECURITY_ERROR ("That card has already been added by another user, reported for fraud!")
	;
	
	private final String errorMessage;
	
	private CARD_ERROR(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
	
	@Override
	public String toString() {
		return errorMessage;
	}

}
