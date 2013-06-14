package com.vee.venmo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public abstract class User {
	private Gateway gateway;
	private String name;
	protected BigDecimal balance = new BigDecimal(0);
	protected List<Card> cards;
	
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
	
	public Iterator<Card> getCards() {
		return cards.iterator();
	}

	Gateway getGateway() {
		return gateway;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public boolean hasAValidCard() {
		return cards.size() !=0;
	}
	/*
	 * END getters
	 */
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	void send(String target, String amount, String msg) 
			throws CardException, UserException, ParseException {
		gateway.send(name, target, amount, msg);
	}
	
	abstract void acknowledge(Payment payment);
	
	abstract boolean receive(Payment payment);
}
