package com.vee.venmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.vee.venmo.exceptions.USER_ERROR;
import com.vee.venmo.exceptions.UserException;

public class VenmoPayUserValidatorTest {

	Gateway gateway;
	@Before
	public void setUp() {
		gateway = new VenmoGateway();   
	}
	
	@Test
	public void testAddUser() {
	    String username1 = "abc";
	    try {
	    	User user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			assertNotNull("User Exists",gateway.getUser(username1));
		} catch (UserException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidUser()  {
        String username1 = "abc";
	    String username2 = "xyz";
	    User user =null;
	    try {
			user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			user = gateway.getUser(username2);
			fail("This should yield exception");
		} catch (UserException e) {
			assertEquals(e.getError(),USER_ERROR.USER_DOESNT_EXIST);
		}
	}
	
	@Test
	public void testInvalidUsername()  {
        String username1 = "abc$%^%%";
	    User user =null;
	    try {
			user = new VenmoUser(gateway, username1);
			gateway.registerUser(user);
			fail("This should yield exception");
		} catch (UserException e) {
			assertEquals(e.getError(),USER_ERROR.INVALID_USERNAME);
		}
	}
}
