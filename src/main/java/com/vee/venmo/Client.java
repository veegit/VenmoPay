package com.vee.venmo;

public class Client 
{
  public static void main(String[] args) 
  {
      Gateway mediator = new VenmoGateway(); 
      
      User user1 = new VenmoUser(mediator, "user1");
      User user2 = new VenmoUser(mediator, "user2");
      User user3 = new VenmoUser(mediator, "user2");
   
      try {
    	mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);
	  } catch (UserException e) {
		e.printStackTrace();
	  }

      user1.send(user2.toString(), 12.20);
      user2.send(user1.toString(),10.20);
      user2.send(user1.toString(),10);
      
      System.out.println(user1.balance);
  }
}