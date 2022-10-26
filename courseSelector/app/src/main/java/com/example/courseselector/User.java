package com.example.courseselector;

import java.util.ArrayList;

public class User {

    public static final int STUDENT_ROLE = 0;
    public static final int INSTRUCTOR_ROLE = 1;
    public static final int ADMIN_ROLE = 2;

    String username;
    String password;
    int role;

    ArrayList<Course> courses = new ArrayList<Course>();

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = STUDENT_ROLE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
