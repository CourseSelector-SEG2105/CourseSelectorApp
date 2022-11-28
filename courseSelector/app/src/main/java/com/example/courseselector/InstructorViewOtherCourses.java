package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstructorViewOtherCourses extends AppCompatActivity {

    public static CourseDatabase database;
    ListView courseListView;
    private ArrayList<String> courseList;
    public static EditText courseCode, courseName;
    Button searchButton, assignButton, unassignButton;
    String instructorUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        instructorUsername = intent.getStringExtra("username");
        database = MainActivity.courseDatabase;
        setContentView(R.layout.instructor_courses_assign_layout);
        courseCode = findViewById(R.id.course_code_field);
        courseName=findViewById(R.id.courseName);
        searchButton = findViewById(R.id.searchButton);
        assignButton = findViewById(R.id.assignButton);
        unassignButton = findViewById(R.id.unassigButton);
        courseListView = findViewById(R.id.list_of_courses);
        courseList = new ArrayList<>();
        viewCourses();

        searchButton.setOnClickListener(v -> {

           if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("")){
              viewCourses();
              Toast.makeText(this, "Course with that code/name does not exist", Toast.LENGTH_SHORT).show();
           } else if(courseName.getText().toString().matches("")) {
               findCourseByCode(courseCode.getText().toString());
           } else if(courseCode.getText().toString().matches("")) {
               findCourseByName(courseName.getText().toString());
           } else {
               Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
           }

        });

        assignButton.setOnClickListener(v -> {
            try {
                if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("")){
                    Toast.makeText(this, "Error: enter a valid course name/code", Toast.LENGTH_SHORT).show();
                } else if(courseName.getText().toString().matches("")) {
                    database.addInstructor(courseCode.getText().toString(), instructorUsername);
                    Toast.makeText(this, "Instructor has been successfully assigned to this course", Toast.LENGTH_SHORT).show();
                } else if(courseCode.getText().toString().matches("")) {
                    database.addInstructor2(courseName.getText().toString(), instructorUsername);
                    Toast.makeText(this, "Instructor has been successfully assigned to this course", Toast.LENGTH_SHORT).show();
                } else if(!courseCode.getText().toString().matches("") && !courseName.getText().toString().matches("")) {
                    database.addInstructor(courseCode.getText().toString(), instructorUsername);
                    Toast.makeText(this, "Instructor has been successfully assigned to this course based on course code", Toast.LENGTH_SHORT).show();
                }
                viewCourses();

            } catch (InstructorExistsException e){
                Toast.makeText(this, "Error: a different instructor is already teaching this course", Toast.LENGTH_SHORT).show();
            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Error: Course does not exist", Toast.LENGTH_SHORT).show();
            }
        });

        unassignButton.setOnClickListener(v -> {
            try {
                if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("")){
                    Toast.makeText(this, "Error: enter a valid course name/code", Toast.LENGTH_SHORT).show();
                } else if(courseName.getText().toString().matches("")) {
                    database.removeInstructor(courseCode.getText().toString(), instructorUsername);
                } else if(courseCode.getText().toString().matches("")) {
                    database.removeInstructor2(courseName.getText().toString(), instructorUsername);
                }
                viewCourses();
                Toast.makeText(this, "Instructor has been successfully unassigned from this course", Toast.LENGTH_SHORT).show();
            } catch (InstructorDoesNotExistException e){
                Toast.makeText(this, "Error: you cannot unassign the instructor from this course", Toast.LENGTH_SHORT).show();
            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Error: Course does not exist", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void viewCourses() {
        courseList.clear();
        Cursor cursor = database.getData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                courseList.add(course.toString3());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }

   private void findCourseByName(String courseName) {
       courseList.clear();
       Cursor cursor = database.getData();
       if (cursor.getCount() == 0) {
           Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
       } else {
           while (cursor.moveToNext()) {
               if(cursor.getString(2).equals(courseName)) {
                   Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                   courseList.add(course.toString3());
               }
           }
       }

       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
       courseListView.setAdapter(adapter);
   }

   private void findCourseByCode(String courseCode) {
       courseList.clear();
       Cursor cursor = database.getData();
       if (cursor.getCount() == 0) {
           Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
       } else {
           while (cursor.moveToNext()) {
               if(cursor.getString(1).equals(courseCode)) {
                   Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                   courseList.add(course.toString3());
               }
           }
       }

       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
       courseListView.setAdapter(adapter);
   }

}
