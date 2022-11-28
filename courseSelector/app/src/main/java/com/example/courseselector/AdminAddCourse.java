package com.example.courseselector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddCourse extends AppCompatActivity {

    public static CourseDatabase database;
    public EditText courseCode;
    public EditText courseName;
    Button addCourse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = MainActivity.courseDatabase;
        setContentView(R.layout.courses_add_layout);
        courseCode = findViewById(R.id.course_code_field);
        courseName = findViewById(R.id.course_name_input);
        addCourse = findViewById(R.id.buttonAddCourse);
        addCourse.setOnClickListener(v -> {
            try {
                database.addCourse(new Course(courseCode.getText().toString(), courseName.getText().toString()));
                Toast.makeText(this, "Course successfully added", Toast.LENGTH_SHORT).show();
            } catch (CourseExistsException e) {
                Toast.makeText(this, "Course already exists", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
