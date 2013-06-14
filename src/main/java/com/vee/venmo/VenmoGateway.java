package com.vee.venmo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.vee.venmo.exceptions.CARD_ERROR;
import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;


public class VenmoGateway implements Gateway {
	
	public static final String usernameRegex = "[a-zA-Z0-9_\\-]+";
	public static final Pattern userPattern = 
		  Pattern.compile(VenmoGateway.usernameRegex);
	private Map<String,User> users = new HashMap<String,User>();
	private Map<Card,User> cards = new HashMap<Card, User>();
	
	public void send(String source, String target, String amountString) 
					throws CardException, UserException, ParseException {
	    send(source, target, amountString,"");			
	}
	
	public void send(String source, String target, String amountString, String message) 
					throws CardException, UserException, ParseException {
		userDoesntExist(source);
		userDoesntExist(target);
		User sourceUser = users.get(source);
		User targetUser = users.get(target);
		hasNoCard(sourceUser);
		Number n = Util.CURRENCY_FORMAT.parse(amountString);
		BigDecimal unroundedAmount = new BigDecimal(n.doubleValue());
		BigDecimal amount = unroundedAmount.setScale(
		 		Util.CURRENCY_FORMAT.getMaximumFractionDigits(),
		        BigDecimal.ROUND_HALF_UP);  
		Payment payment = new Payment(sourceUser, targetUser, amount, message );
		if(targetUser.receive(payment))
		  	sourceUser.acknowledge(payment);			
	}
	
	public void registerUser(User user) throws UserException {
		invalidUserName(user.getName());
		userExists(user.getName());
		users.put(user.getName(),user);
	}
	
	public void registerCard(String userName,String cardNumber) throws CardException, UserException {
		if(!Util.validateCard(cardNumber))
			throw new CardException(cardNumber,CARD_ERROR.INVALID_CARD_ERROR);
		else {
			Card card = new Card(cardNumber);
			if(cards.get(card) != null)
				throw new CardException(cardNumber,CARD_ERROR.CARD_SECURITY_ERROR);
			else {
				userDoesntExist(userName);
				User user = users.get(userName);
				user.addCard(card);
				cards.put(card,user);
			}
		}
	}
	
	public User getUser(String userName) throws UserException {
		userDoesntExist(userName);
		return users.get(userName);
	}
	
	private void userExists(String user) throws UserException {
		if(users.get(user) != null)
			throw new UserException(USER_ERROR.USER_EXISTS);
	}
	
	private void userDoesntExist(String user) throws UserException {
		if(users.get(user) == null)
			throw new UserException(USER_ERROR.USER_DOESNT_EXIST);
	}
	
	private void hasNoCard(User user) throws UserException {
		if(!user.hasAValidCard())
			throw new UserException(USER_ERROR.USER_HASNO_CARD);
	}
	
	private void invalidUserName(String name) throws UserException {
		if(!userPattern.matcher(name).matches())
			throw new UserException(USER_ERROR.INVALID_USERNAME);
	}
}
