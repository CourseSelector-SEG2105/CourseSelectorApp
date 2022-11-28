package com.example.courseselector;

public class StudentAlreadyEnrolled extends Exception {
    public StudentAlreadyEnrolled(String message, Throwable err) {
        super(message, err);
    }
    public StudentAlreadyEnrolled(String message) { super(message); }
}
