package com.example.courseselector;

public class InstructorExistsException extends Exception {
    public InstructorExistsException(String message, Throwable err) {
        super(message, err);
    }
    public InstructorExistsException(String message) { super(message); }
}
