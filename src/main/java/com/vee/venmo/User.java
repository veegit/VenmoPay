package com.vee.venmo;

public abstract class User {
	Gateway mediator;
	String name;
	String card;
	double balance = 0;
	
	User(Gateway m, String name) {
		this.mediator = m;
		this.name = name;
	}
	
	public String getCard() {
		return card;
	}

	public void setCard(String card) throws CardException {
		boolean check = Util.validateCard(card);
		if(!check)
			throw new CardException(card);
		this.card = card;
	}
	
	public double getBalance() {
		return balance;
	}
	
	void send(String target, double amount) {
		mediator.send(name, target, amount);
	}
	
	Gateway getMediator() {
		return mediator;
	}
	
	abstract void receive(User source, double amount);
}
