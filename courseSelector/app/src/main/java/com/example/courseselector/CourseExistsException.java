package com.example.courseselector;

public class CourseExistsException extends Exception {
    public CourseExistsException(String message, Throwable err) {
        super(message, err);
    }
    public CourseExistsException(String message) { super(message); }
}
