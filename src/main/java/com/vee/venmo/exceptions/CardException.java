package com.vee.venmo.exceptions;

public class CardException extends Exception {

	private static final long serialVersionUID = -2450382470987073362L;

	public CardException(String card, CARD_ERROR error) {
		super("ERROR: " + error.toString());
	}

}
