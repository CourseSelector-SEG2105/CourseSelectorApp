package com.example.courseselector;

import junit.framework.TestCase;
import org.junit.Test;


public class AuthenticationTest extends TestCase {

    @Test
    public void testIsValidEmail() {
        Authentication authenticator = new Authentication();

        //Invalid emails
        assertFalse(authenticator.isValidEmail("invalid email"));
        assertFalse(authenticator.isValidEmail("invalid email @gmail.com"));
        assertFalse(authenticator.isValidEmail("invalidemail@.com"));
        assertFalse(authenticator.isValidEmail("@email.com"));
        assertFalse(authenticator.isValidEmail(""));

        //valid emails
        assertTrue(authenticator.isValidEmail("someemail.email@email.com"));
    }

    @Test
    public void testIsValidPassword() {
        Authentication authenticator = new Authentication();

        assertTrue(authenticator.isValidPassword("Any!@#Password0()='"));
        assertFalse(authenticator.isValidPassword(""));
    }
}