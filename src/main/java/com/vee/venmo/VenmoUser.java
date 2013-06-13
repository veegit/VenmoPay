package com.vee.venmo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VenmoUser extends User {

	List<Payment> payments = new ArrayList<Payment>();
	
	VenmoUser(Gateway m, String name) {
		super(m, name);
	}
	
	//TODO Make an iterator
	void getPaymentFeed() {
		for(Payment payment: payments) {
			if(payment.getFrom() == this)
				System.out.println("You paid " + payment.getTo() + " " + 
						Util.CURRENCY_FORMAT.format(payment.getAmount()));
			else
				System.out.println(payment.getTo() + " paid you " + 
						Util.CURRENCY_FORMAT.format(payment.getAmount()));
		}
	}

	private void incrementBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}

	@Override
	synchronized boolean receive(Payment payment) {
		incrementBalance(payment.getAmount());
		payments.add(payment);
		System.out.println(this + ": Received from " + payment.getFrom() 
				+ " $" + payment.getAmount());
		return true;
	}
	
	synchronized void acknowledge(Payment payment) {
		payments.add(payment);
	}

	@Override
	public String toString() {
		return super.getName();
	}

}
