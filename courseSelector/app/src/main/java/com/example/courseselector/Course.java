package com.example.courseselector;

import java.util.ArrayList;

public class Course {
    String courseCode;
    String courseName;
    String courseInstructor;
    String courseDescription;
    String courseDay;
    String courseStartTime;
    String courseEndTime;
    int courseCapacity;
    private static final String NA = "NA";

    public Course(String courseCode, String courseName){
        this.courseCode=courseCode;
        this.courseName=courseName;
        this.courseInstructor= NA;
        this.courseDescription= NA;
        this.courseDay = NA;
        this.courseStartTime = NA;
        this.courseEndTime = NA;
        this.courseCapacity = -1;
    }

    public Course(String courseCode, String courseName, String courseInstructor, String courseDescription, String courseDay, String courseStartTime, String courseEndTime, int courseCapacity){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseInstructor = courseInstructor;
        this.courseDescription = courseDescription;
        this.courseDay = courseDay;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.courseCapacity = courseCapacity;
    }

    public Course (String courseCode, String courseName, String courseDay){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDay = courseDay;
        this.courseInstructor= NA;
        this.courseDescription= NA;
        this.courseStartTime = NA;
        this.courseEndTime = NA;
        this.courseCapacity = -1;
    }

    
    // Method to get the course code 
    public String getCourseCode(){return courseCode;}

    public void setCourseCode(String courseCode){this.courseCode=courseCode;}

    public String getCourseName(){return courseName;}

    public void setCourseName(String courseName){this.courseName=courseName;}

    public String toString() {
        return String.format("%s (%s)", courseName, courseCode);
    }

    public String toString2() {
        return ""+ courseName +" (" + courseCode + "): " + courseDescription + "; \nInstructor: " +courseInstructor + String.format("\nSchedule: %s (%s-%s)", courseDay, courseStartTime, courseEndTime) + "\nCapacity: " + courseCapacity;
    }

    public String toString3(){
        return  "" + courseName +" (" + courseCode + "): " + courseInstructor;
    }

    public String toString4(){
        return ""+ courseName +" (" + courseCode + ") on " + courseDay + "s";
    }


    public String getCourseDescription(){
        return courseDescription;
    }
    
    public void setCourseDescription(String description){
        this.courseDescription=description;
    }
    
    public String getCourseInstructor(){
        return courseInstructor;
    }

    public void setCourseInstructor(String instructor){
        this.courseInstructor=instructor;
    }

    public String getCourseTiming(){
        return courseDay;
    }

    public void setCourseDay(String courseDay){
        this.courseDay=courseDay;
    }

    public String getCourseStartTime() {
        return this.courseStartTime;
    }

    public void setCourseStartTime(String courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public String getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public int getCourseCapacity(){
        return courseCapacity;
    }

    public void setCourseCapacity(int courseCapacity){
        this.courseCapacity=courseCapacity;
    }
}
