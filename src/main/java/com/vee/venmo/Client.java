package com.vee.venmo;

import java.io.File;
import java.io.FileNotFoundException;
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
  
  void test() {
	  
      User user1 = new VenmoUser(gateway, "user1");
      User user2 = new VenmoUser(gateway, "user2");
      User user3 = new VenmoUser(gateway, "user2");
   
      try {
    	gateway.registerUser(user1);
        gateway.registerUser(user2);
	  } catch (UserException e) {
		e.printStackTrace();
	  }

      try {
		user1.send(user2.toString(), "$12.20", "blah");
		user2.send(user1.toString(), "$10.20", "blah");
	    //user2.send(user1.toString(),"$10");
      } catch (ParseException e) {
		e.printStackTrace();
      }
      	catch (CardException e) {
		e.printStackTrace();
      } catch (UserException e) {
		e.printStackTrace();
	  }
     
      //((VenmoUser) user2).getPaymentFeed();
      System.out.println(user1.getBalance());
  }
  
  void parseInput() {
	  parseInput(true);
  }
  
  void parseInput(boolean echo) {
	  Scanner scanner=null;
	try {
		scanner = new Scanner(new File("sampleinput.txt"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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