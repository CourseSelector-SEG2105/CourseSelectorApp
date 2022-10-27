package com.example.courseselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class AdminLoginScreen extends AppCompatActivity {

    TextView welcomeMessageField;
    Button addUserBtn, deleteUserBtn, addCourseBtn, deleteCourseBtn, editCourseBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login_screen);

//        Buttons
        addUserBtn =  findViewById(R.id.add_user_btn);
        addCourseBtn = findViewById(R.id.course_add_btn);
        deleteUserBtn = findViewById(R.id.delete_user_btn);
        deleteCourseBtn = findViewById(R.id.delete_course_btn);
        editCourseBtn = findViewById(R.id.edit_course_btn);

//        Welcome Message
        welcomeMessageField = findViewById(R.id.welcomeMessage);
        Intent intent = getIntent();
        String welcomeMessage = "Welcome " + intent.getStringExtra("username") + "! (" + intent.getStringExtra("role") + ")";
        welcomeMessageField.setText(welcomeMessage);

//        Button on click listeners
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminLoginScreen.this,adminAddUser.class);
                startActivity(intent2);
            }
        });

        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdminLoginScreen.this,AdminDeleteUser.class);
                startActivity(intent3);
            }
        });

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AdminLoginScreen.this,AdminAddCourse.class);
                startActivity(intent4);
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(AdminLoginScreen.this,AdminDeleteCourse.class);
                startActivity(intent5);
            }
        });

        editCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(AdminLoginScreen.this,AdminEditCourse.class);
                startActivity(intent6);
            }
        });


    }
}