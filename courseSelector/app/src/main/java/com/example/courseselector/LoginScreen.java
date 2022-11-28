package com.example.courseselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginScreen extends AppCompatActivity {

    TextView welcomeMessageField;
    Button viewOtherCourses, viewMyCourses;
    String studentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_login_screen);

        welcomeMessageField = findViewById(R.id.welcomeMessage);

//        Buttons
        viewOtherCourses =  findViewById(R.id.student_viewOther);
        viewMyCourses = findViewById(R.id.student_viewMy);

//        Welcome message
        welcomeMessageField = findViewById(R.id.welcomeMessage);
        Intent intent = getIntent();
        studentUsername = intent.getStringExtra("username");
        String welcomeMessage = "Welcome " + intent.getStringExtra("username") + "! (" + intent.getStringExtra("role") + ")";
        welcomeMessageField.setText(welcomeMessage);

        //        Button on click listeners
        viewOtherCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginScreen.this, StudentViewOtherCourses.class);
                intent2.putExtra("username", studentUsername);
                startActivity(intent2);
            }
        });

        viewMyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(LoginScreen.this,StudentViewMyCourses.class);
                intent3.putExtra("username", studentUsername);
                startActivity(intent3);
            }
        });


    }
}