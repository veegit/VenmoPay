package com.vee.venmo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VenmoUser extends User {

	private List<Payment> payments = new ArrayList<Payment>();
	
	VenmoUser(Gateway m, String name) {
		super(m, name);
	}
	
	
	public Iterator<Payment> getPayments() {
		return payments.iterator();
	}

	private void incrementBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}

	@Override
	synchronized boolean receive(Payment payment) {
		incrementBalance(payment.getAmount());
		payments.add(payment);
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
