package com.vee.venmo;

import java.text.ParseException;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public interface Gateway {
	public void send(String source, String target, String amountString) throws ParseException;
	public void registerUser(User user) throws UserException;
	public void registerCard(String user,String cardNumber) throws CardException, UserException;
}
