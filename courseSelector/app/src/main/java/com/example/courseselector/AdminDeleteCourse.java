package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDeleteCourse extends AppCompatActivity {

    public static CourseDatabase database;
    public static EditText courseCode;
    public static EditText courseName;
    Button deleteCourse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        database = MainActivity.courseDatabase;
        setContentView(R.layout.courses_delete_layout);
        courseCode = findViewById(R.id.course_code_field);
        deleteCourse = findViewById(R.id.deleteCourseBtn);
        deleteCourse.setOnClickListener(v -> {
            try {
                database.deleteCourse(courseCode.getText().toString());
                Toast.makeText(this, "Course successfully deleted", Toast.LENGTH_SHORT).show();
            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Course with that code does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
