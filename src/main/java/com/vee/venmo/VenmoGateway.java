package com.vee.venmo;

import java.util.HashMap;
import java.util.Map;


public class VenmoGateway implements Gateway {
	
	Map<String,User> users = new HashMap<String,User>();
	
	@Override
	public void send(String source, String target, double amount) {
		    User sourceUser = users.get(source);
		    User targetUser = users.get(target);
			targetUser.receive(sourceUser,amount);
	}
	
	@Override
	public void addUser(User user) throws UserException {
		if(users.get(user.name) != null)
			throw  new UserException();
		users.put(user.name,user);
	}

}
