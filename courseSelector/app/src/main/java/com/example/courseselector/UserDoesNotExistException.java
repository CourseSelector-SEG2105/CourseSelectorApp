package com.example.courseselector;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String message, Throwable err) {
        super(message, err);
    }
    public UserDoesNotExistException(String message) { super(message); }
}
