package com.vee.venmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.vee.venmo.exceptions.CARD_ERROR;
import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;

public class VenmoPayFeedValidatorTest {

	Gateway gateway;
	String aTestUsername;
	User aTestUser;
	
	@Before
	public void setUp() {
		gateway = new VenmoGateway();
		aTestUsername = "abc";
		aTestUser = new VenmoUser(gateway, aTestUsername);
    	try {
    		gateway.registerUser(aTestUser);
			gateway.registerCard(aTestUsername,"4111111111111111");
		} catch (CardException e) {
			
		} catch (UserException e) {
			
		}
	}
	
	@Test
	public void testGetFeed() {
	    try {
	    	String username1 = "xyz";
		    User user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			gateway.registerCard(username1, "5555555555554444");
			String amountStr = "10";
			aTestUser.send(username1, "$" + amountStr, "first");
			user.send(aTestUsername, "$" + amountStr, "repay");
			Iterator<Payment> it = ((VenmoUser)aTestUser).getPayments();
			int count = 0;
			while(it.hasNext()) {
				count++;
				it.next();
			}
			assertEquals(count,2);
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail("Invalid amount");
		}
	}
}
