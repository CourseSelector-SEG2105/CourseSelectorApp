package com.example.courseselector;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {
    User testUser = new User("username", "password");

    @Test
    public void testUserPrint() {
        assertEquals("username (Student)", testUser.toString());
    }

    @Test
    public void testUserPrintUpdated() {
        testUser.setRole(User.INSTRUCTOR_ROLE);
        assertEquals("username (Teacher)", testUser.toString());
    }

    @Test
    public void testUserGet() {
        testUser.setRole(User.INSTRUCTOR_ROLE);
        assertEquals(1, testUser.getRole());

        assertEquals("username", testUser.getUsername());
        assertEquals("password", testUser.getPassword());
    }
}