package com.example.courseselector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminLoginScreen extends AppCompatActivity {

    TextView welcomeMessageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login_screen);

        welcomeMessageField = findViewById(R.id.welcomeMessage);

        Intent intent = getIntent();
        String welcomeMessage = "Welcome " + intent.getStringExtra("username") + "! (" + intent.getStringExtra("role") + ")";
        welcomeMessageField.setText(welcomeMessage);;
    }


}