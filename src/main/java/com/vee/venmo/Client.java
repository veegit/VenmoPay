package com.vee.venmo;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public class Client {
  
  public static final Pattern userInputPattern = 
		  Pattern.compile(String.format("user\\s*(%s)$",VenmoGateway.usernameRegex));  
  private static final Pattern addPattern = 
	  Pattern.compile(String.format("add\\s*(%s)\\s*(\\d+)$",VenmoGateway.usernameRegex));
  private static final Pattern payPattern = 
	  Pattern.compile(String.format("pay\\s*(%s)\\s*(%s)\\s*(.[0-9\\.]+)(.*)$",
			  VenmoGateway.usernameRegex,VenmoGateway.usernameRegex));
  private static final Pattern feedPattern = 
	  Pattern.compile(String.format("feed\\s*(%s)$",VenmoGateway.usernameRegex));
  private static final Pattern balancePattern = 
	  Pattern.compile(String.format("balance\\s*(%s)$",VenmoGateway.usernameRegex));
  
  Gateway gateway = new VenmoGateway(); 
  
  public static void main(String[] args) 
  {
	 Client client = new Client();
	 client.parseInput();
  }
  
  void parseInput() {
	  parseInput(true);
  }
  
  void parseInput(boolean echo) {
	  Scanner scanner=new Scanner(System.in);
	  while(scanner.hasNextLine()) {
		String input = scanner.nextLine();
		if(echo)
			System.out.println("> " + input);
		Matcher userMatcher = userInputPattern.matcher(input);
		Matcher addMatcher = addPattern.matcher(input);
		Matcher payMatcher = payPattern.matcher(input);
		Matcher feedMatcher = feedPattern.matcher(input);
		Matcher balanceMatcher = balancePattern.matcher(input);
		if(userMatcher.find())
			addUser(userMatcher.group(1));
		else if(addMatcher.find())
			addCard(addMatcher.group(1),addMatcher.group(2));
		else if(payMatcher.find())
			addPayment(payMatcher.group(1),payMatcher.group(2),
					payMatcher.group(3),payMatcher.group(4));
		else if(feedMatcher.find())
			getFeed(feedMatcher.group(1));
		else if(balanceMatcher.find())
			getBalance(balanceMatcher.group(1));
		else
			System.out.println("Wrong Input " + input);
	}
  }
  
  public void addUser(String u) {
	  User user = new VenmoUser(gateway, u);
	  try {
		gateway.registerUser(user);
	} catch (UserException e) {
		System.out.println(e.getMessage());
	}
  }
  
  public void addCard(String u, String c) {
	  try {
		gateway.registerCard(u, c);
	} catch (CardException e) {
		System.out.println(e.getMessage());
	} catch (UserException e) {
		System.out.println(e.getMessage());
	}
  }
  
  public void addPayment(String u1, String u2, String amount, String msg) {
	try {
		User user1 = gateway.getUser(u1);
		user1.send(u2, amount, msg.trim());
	} catch (ParseException e) {
		System.out.println(e.getMessage());
	} catch (CardException e) {
		System.out.println(e.getMessage());
	} catch (UserException e) {
		System.out.println(e.getMessage());
	}
  }
  
  public void getFeed(String u1) {
	try {
		User user = gateway.getUser(u1);
		Iterator<Payment> payments = ((VenmoUser) user).getPayments();
		while(payments.hasNext()) {
		  Payment payment = payments.next();
		  if(payment.getFrom() == user)
				System.out.println("-- You paid " + payment.getTo() + " " + 
						Util.CURRENCY_FORMAT.format(payment.getAmount()) + " "
						+ payment.getMessage());
		  else
				System.out.println("-- " + payment.getFrom() + " paid you " + 
						Util.CURRENCY_FORMAT.format(payment.getAmount()) + " for "
						+ payment.getMessage());
		}
	} catch (UserException e) {
		System.out.println(e.getMessage());
	}
  }
  
  public void getBalance(String u1) {
	  try {
		  User user	= gateway.getUser(u1);
		  System.out.println("-- " + Util.CURRENCY_FORMAT.format(user.getBalance()));
		} catch (UserException e) {
			System.out.println(e.getMessage());
	  }
  }
}