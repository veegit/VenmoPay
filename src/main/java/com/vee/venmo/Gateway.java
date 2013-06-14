package com.vee.venmo;

import java.text.ParseException;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public interface Gateway {
	public void send(String source, String target, String amountString, String msg) 
					throws CardException, UserException, ParseException;
	public void send(String source, String target, String amountString) 
					throws CardException, UserException, ParseException;
	public void registerUser(User user) throws UserException;
	public void registerCard(String user,String cardNumber) throws CardException, UserException;
	public User getUser(String userName) throws UserException;
}
