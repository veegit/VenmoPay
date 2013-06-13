package com.vee.venmo;

public interface Gateway {
	public void send(String source, String target, double amount);
	public void addUser(User user) throws UserException;
}
