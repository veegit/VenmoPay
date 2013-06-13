package com.vee.venmo;

public class CardException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;
	
	public CardException(String message) {
		super(message);
	}
	
	public CardException() {
		super("Invalid Card, Try again");
	}

}
