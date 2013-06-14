package com.vee.venmo.exceptions;

public class CardException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;
	private CARD_ERROR error;
	
	public CardException(String card, CARD_ERROR error) {
		super("ERROR: " + error.toString());
		this.error = error;
	}
	
	public CARD_ERROR getError() {
		return error;
	}

}
