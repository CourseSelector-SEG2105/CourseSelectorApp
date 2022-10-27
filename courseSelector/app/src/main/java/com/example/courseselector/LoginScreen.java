package com.example.courseselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginScreen extends AppCompatActivity {

    TextView welcomeMessageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        welcomeMessageField = findViewById(R.id.welcomeMessage);

        Intent intent = getIntent();
        String welcomeMessage = "Welcome " + intent.getStringExtra("username") + "! (" + intent.getStringExtra("role") + ")";
        welcomeMessageField.setText(welcomeMessage);

    }
}