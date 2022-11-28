package com.example.courseselector;

import junit.framework.TestCase;

import org.junit.Test;

public class CourseTest extends TestCase {

    Course course = new Course("Sample Code", "Sample Name");

    @Test
    public void testCoursePrint() {

        assertEquals("Sample Name (Sample Code)", course.toString());
        assertEquals("Sample Name (Sample Code): NA; \nInstructor: NA\nSchedule: NA\nCapacity: NA", course.toString2());
        assertEquals("Sample Name (Sample Code): NA", course.toString3());
    }

    @Test
    public void testCourseSetting() {
        course.setCourseName("Sample Name 2");
        assertEquals("Sample Name 2", course.getCourseName());

        course.setCourseInstructor("InstructorName");
        assertEquals("Sample Name 2 (Sample Code): InstructorName", course.toString3());

        course.setCourseCode("SampleCode");
        assertEquals("SampleCode", course.getCourseCode());
    }
}