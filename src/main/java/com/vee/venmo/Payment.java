package com.vee.venmo;

import java.math.BigDecimal;

public class Payment {
	
	//TODO add a message field
	private User from;
	private User to;
	private BigDecimal amount;
	
	Payment(User from, User to, BigDecimal amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	public User getFrom() {
		return from;
	}

	public User getTo() {
		return to;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
