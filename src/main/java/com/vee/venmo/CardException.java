package com.vee.venmo;

public class CardException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;
	
	public CardException(String Card, String message) {
		super(message);
	}
	
	public CardException(String card) {
		super("Invalid Card " + card + ". Try again");
	}

}
