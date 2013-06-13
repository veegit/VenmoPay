package com.vee.venmo;

public class VenmoUser extends User {

	VenmoUser(Gateway m, String name) {
		super(m, name);
	}

	private void incrementBalance(double amount) {
		balance += amount;
	}

	@Override
	void receive(User source, double amount) {
		incrementBalance(amount);
		System.out.println(this + ": Received from " + source + " $" + amount);
	}

	@Override
	public String toString() {
		return name;
	}

}
