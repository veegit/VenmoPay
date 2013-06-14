package com.vee.venmo;

import java.math.BigDecimal;
import java.text.ParseException;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public abstract class User {
	private Gateway gateway;
	private String name;
	private Card card;
	protected BigDecimal balance = new BigDecimal(0);
	
	User(Gateway m, String name) {
		this.gateway = m;
		this.name = name;
	}
	
	/*
	 * BEGIN getters
	 */
	public String getName() {
		return name;
	}
	
	public Card getCard() {
		return card;
	}

	Gateway getgateway() {
		return gateway;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	/*
	 * END getters
	 */
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	void send(String target, String amount, String msg) 
			throws CardException, UserException, ParseException {
		gateway.send(name, target, amount, msg);
	}
	
	abstract void acknowledge(Payment payment);
	
	abstract boolean receive(Payment payment);
}
