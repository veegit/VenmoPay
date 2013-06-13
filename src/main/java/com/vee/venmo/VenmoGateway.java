package com.vee.venmo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.vee.venmo.exceptions.CARD_ERROR;
import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;


public class VenmoGateway implements Gateway {
	
	Map<String,User> users = new HashMap<String,User>();
	Map<Card,User> cards = new HashMap<Card, User>();
	
	public void send(String source, String target, String amountString) throws ParseException {
		    User sourceUser = users.get(source);
		    User targetUser = users.get(target);
		    Number n = Util.CURRENCY_FORMAT.parse(amountString);
		    BigDecimal unroundedAmount = new BigDecimal(n.doubleValue());
		    BigDecimal amount = unroundedAmount.setScale(
		    		Util.CURRENCY_FORMAT.getMaximumFractionDigits(),
		            BigDecimal.ROUND_HALF_UP);  
		    Payment payment = new Payment(sourceUser, targetUser, amount );
		    if(targetUser.receive(payment))
		    	sourceUser.acknowledge(payment);			
	}
	
	public void registerUser(User user) throws UserException {
		userExists(user.getName());
		users.put(user.getName(),user);
	}
	
	public void registerCard(String user,String cardNumber) throws CardException, UserException {
		if(!Util.validateCard(cardNumber))
			throw new CardException(cardNumber,CARD_ERROR.INVALID_CARD_ERROR);
		else if(cards.get(cardNumber) != null)
			throw new CardException(cardNumber,CARD_ERROR.CARD_SECURITY_ERROR);
		else {
			userDoesntExist(user);
			Card card = new Card(cardNumber);
			cards.put(card,users.get(user));
		}
	}
	
	private void userExists(String user) throws UserException {
		if(users.get(user) != null)
			throw new UserException(USER_ERROR.USER_EXISTS);
	}
	
	private void userDoesntExist(String user) throws UserException {
		if(users.get(user) == null)
			throw new UserException(USER_ERROR.USER_DOESNT_EXIST);
	}
}
