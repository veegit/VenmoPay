package com.vee.venmo;

public class Card {
	private String number;
	
	Card(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Card))
			return false;
		return number.equals(((Card) o).getNumber());
	}
	
	@Override
	public int hashCode() {
		return number.hashCode();
	}
}
