package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddUser extends AppCompatActivity {

    public static UserDatabase database;
    public EditText username;
    public EditText password;
    Authentication authenticator = new Authentication();
    CheckBox instructorCheckbox;
    Button addUser;
    int userRole;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new UserDatabase(this);
        setContentView(R.layout.users_add_layout);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        addUser = findViewById(R.id.buttonAddUser);
        instructorCheckbox = findViewById(R.id.instructorCheck);

        addUser.setOnClickListener(v -> {
            if (instructorCheckbox.isChecked()){
                userRole = User.INSTRUCTOR_ROLE;
            } else {
                userRole = User.STUDENT_ROLE;
            }
            User addUser = new User(username.getText().toString(), password.getText().toString(), userRole);
            authenticator.signup(addUser, database, this);
        });
    }

}
