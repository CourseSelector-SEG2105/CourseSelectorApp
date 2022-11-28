package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class InstructorLoginScreen extends AppCompatActivity {

    TextView welcomeMessageField;
    Button viewOtherCourses, viewMyCourses, viewMyStudents;
    String instructorUsername;


//    @SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_activity_login_screen);

//        Buttons
        viewOtherCourses =  findViewById(R.id.ViewOtherCoursesButton);
        viewMyCourses = findViewById(R.id.ViewMyCoursesButton);
        viewMyStudents = findViewById(R.id.viewStudentsButton);

//        Welcome Message
        welcomeMessageField = findViewById(R.id.welcomeMessage);
        Intent intent = getIntent();
        instructorUsername = intent.getStringExtra("username");
        String welcomeMessage = "Welcome " + intent.getStringExtra("username") + "! (" + intent.getStringExtra("role") + ")";
        welcomeMessageField.setText(welcomeMessage);

//        Button on click listeners
        viewOtherCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(InstructorLoginScreen.this, InstructorViewOtherCourses.class);
                intent2.putExtra("username", instructorUsername);
                startActivity(intent2);
            }
        });

        viewMyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(InstructorLoginScreen.this,InstructorViewMyCourses.class);
                intent3.putExtra("username", instructorUsername);
                startActivity(intent3);
            }
        });

        viewMyStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(InstructorLoginScreen.this,InstructorViewStudents.class);
                intent4.putExtra("username", instructorUsername);
                startActivity(intent4);
            }
        });


    }
}