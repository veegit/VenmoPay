package com.vee.venmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.vee.venmo.exceptions.CARD_ERROR;
import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;

public class VenmoPayCardValidatorTest {

	Gateway gateway;
	String username;
	@Before
	public void setUp() {
		gateway = new VenmoGateway();
		username = "abc";
	}
	
	@Test
	public void testRegisterCard1() {
	    try {
	    	User user = new VenmoUser(gateway, username);
	    	gateway.registerUser(user);
			gateway.registerCard(username,"4111111111111111");
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testRegisterCard2()  {
		try {
	    	User user = new VenmoUser(gateway, username);
	    	gateway.registerUser(user);
			gateway.registerCard(username,"1234567890123456");
			fail("Should yield Exception");
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			assertEquals(e.getError(), CARD_ERROR.INVALID_CARD_ERROR);
		}
	}
	
	@Test
	public void testRegisterCard3()  {
		try {
	    	User user = new VenmoUser(gateway, username);
	    	gateway.registerUser(user);
			gateway.registerCard(username,"4111111111111111");
			username = "xyz";
			user = new VenmoUser(gateway, username);
	    	gateway.registerUser(user);
			gateway.registerCard(username,"4111111111111111");
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			assertEquals(e.getError(), CARD_ERROR.CARD_SECURITY_ERROR);
		}
	}
	
	@Test
	public void testInvalidUsername()  {
        String username1 = "abc$%^%%";
	    User user =null;
	    try {
			user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			gateway.registerCard(username,"1234567890123456");
			fail("This should yield exception");
		} catch (UserException e) {
			assertEquals(e.getError(),USER_ERROR.INVALID_USERNAME);
		} catch (CardException e) {
			fail(e.getMessage());
		}
	}
}
