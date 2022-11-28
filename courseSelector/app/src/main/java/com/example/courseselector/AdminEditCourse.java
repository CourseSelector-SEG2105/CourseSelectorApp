package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminEditCourse extends AppCompatActivity {

    public static CourseDatabase database;
    public static EditText courseCode;
    public static EditText newCourseName;
    public static EditText newCourseCode;
    Button editCourse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        database = MainActivity.courseDatabase;
        setContentView(R.layout.courses_edit_layout);
        courseCode = findViewById(R.id.course_code_field);
        newCourseName = findViewById(R.id.course_name_input);
        newCourseCode = findViewById(R.id.editCourseCodeField);
        editCourse = findViewById(R.id.saveChangesBtn);
        editCourse.setOnClickListener(v -> {
            try {
                database.editCourse(courseCode.getText().toString(), newCourseName.getText().toString(), newCourseCode.getText().toString());
                Toast.makeText(this, "Course changes have been successfully saved", Toast.LENGTH_SHORT).show();
            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Course with that code does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }

}