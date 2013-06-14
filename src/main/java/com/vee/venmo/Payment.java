package com.vee.venmo;

import java.math.BigDecimal;

public class Payment {
	
	private User from;
	private User to;
	private BigDecimal amount;
	private String message;
	
	Payment(User from, User to, BigDecimal amount,String message) {
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.message = message;
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
	
	public String getMessage() {
		return message;
	}
}
