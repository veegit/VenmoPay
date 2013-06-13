package com.vee.venmo;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.UserException;

public class Client 
{
  static Gateway gateway = new VenmoGateway(); 
    	
  public static void main(String[] args) 
  {
	  parseInput();
  }
  
  static void test() {
	  
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
		user1.send(user2.toString(), "$12.20");
		user2.send(user1.toString(), "$10.20");
	    //user2.send(user1.toString(),"$10");
	} catch (ParseException e) {
		e.printStackTrace();
	}
      ((VenmoUser) user2).getPaymentFeed();
      
      System.out.println(user1.getBalance());
  }
  
  static void parseInput() {
	  Pattern userPattern = Pattern.compile("user\\s*([a-zA-Z0-9_\\-]+)"); 
	  Pattern addPattern = Pattern.compile("add\\s*([a-zA-Z0-9_\\-]+)\\s*(\\d+)");
	  Pattern payPattern = Pattern.compile("pay\\s*([a-zA-Z0-9_\\-]+)\\s*([a-zA-Z0-9_\\-]+)\\s*(.[0-9\\.]+)(.*)");
	  try {
		Scanner scanner = new Scanner(new File("sampleInput.txt"));
		while(scanner.hasNextLine()) {
			String input = scanner.nextLine();
			Matcher userMatcher = userPattern.matcher(input);
			Matcher addMatcher = addPattern.matcher(input);
			Matcher payMatcher = payPattern.matcher(input);
			if(userMatcher.find())
				addUser(userMatcher.group(1));
			else if(addMatcher.find())
				addCard(addMatcher.group(1),addMatcher.group(2));
			else if(payMatcher.find())
				addPayment(payMatcher.group(1),payMatcher.group(2),
						payMatcher.group(3),payMatcher.group(4));
			else
				System.err.println("No match");
		}	
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
  }
  
  static void addUser(String u) {
	  User user = new VenmoUser(gateway, u);
	  try {
		gateway.registerUser(user);
	} catch (UserException e) {
		System.err.println(e.getMessage());
	}
  }
  
  static void addCard(String u, String c) {
	  try {
		gateway.registerCard(u, c);
	} catch (CardException e) {
		System.err.println(e.getMessage());
	} catch (UserException e) {
		System.err.println(e.getMessage());
	}
  }
  
  static void addPayment(String u1, String u2, String amount, String msg) {
	  User user1 = ((VenmoGateway) gateway).users.get(u1);
	  try {
		user1.send(u2, amount);
	} catch (ParseException e) {
		e.printStackTrace();
	}
  }
}