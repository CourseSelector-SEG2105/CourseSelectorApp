package com.example.courseselector;

public class UserExistsException extends Exception {
    public UserExistsException(String message, Throwable err) {
        super(message, err);
    }
    public UserExistsException(String message) { super(message); }
}
