package com.example.courseselector;

public class TimeConflictException extends Exception{
    public TimeConflictException(String message, Throwable err) {
        super(message, err);
    }
    public TimeConflictException(String message) { super(message); }
}
