package com.vee.venmo;

import java.util.HashMap;
import java.util.Map;


public class VenmoGateway implements Gateway {
	
	Map<String,User> map = new HashMap<String,User>();
	
	@Override
	public void send(String source, String target, double amount) {
		    User sourceUser = map.get(source);
		    User targetUser = map.get(target);
			targetUser.receive(sourceUser,amount);
	}
	
	@Override
	public void addColleague(User user) {
		map.put(user.name,user);
	}

}
