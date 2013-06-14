package com.vee.venmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.vee.venmo.exceptions.CARD_ERROR;
import com.vee.venmo.exceptions.CardException;
import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;

public class VenmoPayBalanceValidatorTest {

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
	public void testGetBalance() {
	    try {
	    	String username1 = "xyz";
		    User user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			gateway.registerCard(username1, "5555555555554444");
			String amountStr = "10";
			aTestUser.send(username1, "$" + amountStr, "first");
			BigDecimal amount = user.getBalance();
			assertEquals(0,amount.compareTo(new BigDecimal(amountStr)));
		} catch (UserException e) {
			fail(e.getMessage());
		} catch (CardException e) {
			fail(e.getMessage());
		} catch (ParseException e) {
			fail("Invalid amount");
		}
	}
}
