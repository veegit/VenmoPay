package com.vee.venmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;

public class VenmoPayPaymentValidatorTest {

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
	public void testMakePayment() {
	    try {
	    	String username = "xyz";
			User user = new VenmoUser(gateway, username);
			gateway.registerUser(user);
			gateway.registerCard(username,"5454545454545454");
			aTestUser.send(username, "$12.20", "message");
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail("Input Error in amount");
		}
	}
	
	@Test
	public void testHasNoCard()  {
		 try {
		    String username1 = "xyz";
			User user1 = new VenmoUser(gateway, username1);
			gateway.registerUser(user1);
			String username2 = "pqr";
			User user2 = new VenmoUser(gateway, username2);
			gateway.registerUser(user2);
			user1.send(username2, "$12.20", "message");
		} catch (UserException e) {
			assertEquals(e.getError(),USER_ERROR.USER_HASNO_CARD);
		} catch (CardException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail("Input Error in amount");
		}
	}
	
	@Test
	public void testNoSuchUser()  {
		 try {
		    String username2 = "pqr";
			aTestUser.send(username2, "$12.20", "message");
		} catch (UserException e) {
			assertEquals(e.getError(),USER_ERROR.USER_DOESNT_EXIST);
		} catch (CardException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail("Input Error in amount");
		}
	}

}
