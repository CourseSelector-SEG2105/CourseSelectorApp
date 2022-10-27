package com.example.courseselector;

public class CourseDoesNotExistException extends Exception {
    public CourseDoesNotExistException(String message, Throwable err) {
        super(message, err);
    }
    public CourseDoesNotExistException(String message) { super(message); }
}
