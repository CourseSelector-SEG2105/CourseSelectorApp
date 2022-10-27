package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class adminAddUser extends AppCompatActivity {

    public static UserDatabase database;
    public static EditText username;
    public static EditText password;
    CheckBox instructorCheckbox;
    Button addUser;
    int userRole;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        database = MainActivity.userDatabase;
        setContentView(R.layout.users_add_layout);
        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        addUser = findViewById(R.id.buttonAddUser);
        instructorCheckbox = findViewById(R.id.instructorCheck);

        if (instructorCheckbox.isChecked()){
            userRole = User.INSTRUCTOR_ROLE;
        } else {
            userRole = User.STUDENT_ROLE;
        }

        addUser.setOnClickListener(v -> {
            try {
                database.addUser(new User(username.getText().toString(), password.getText().toString(),userRole));
                Toast.makeText(adminAddUser.this, "User successfully added", Toast.LENGTH_SHORT).show();
            } catch (UserExistsException e) {
                Toast.makeText(adminAddUser.this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
