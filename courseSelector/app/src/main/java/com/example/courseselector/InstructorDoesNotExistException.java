package com.example.courseselector;

public class InstructorDoesNotExistException extends Exception {
    public InstructorDoesNotExistException(String message, Throwable err) {
        super(message, err);
    }
    public InstructorDoesNotExistException(String message) { super(message); }
}
