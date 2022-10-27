package com.example.courseselector;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteUser extends AppCompatActivity {

    public static UserDatabase database;
    public static EditText username;
    public static EditText password;
    CheckBox instructorCheckbox;
    Button deleteUserBtn;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent2 = getIntent();
        database = MainActivity.userDatabase;
        setContentView(R.layout.users_delete_layout);
        username = findViewById(R.id.usernameField);
        deleteUserBtn = findViewById(R.id.delete_user_button);
        deleteUserBtn.setOnClickListener(v -> {
            try {
                database.deleteUser(username.getText().toString());
                Toast.makeText(this, "User successfully deleted", Toast.LENGTH_SHORT).show();
            } catch (UserDoesNotExistException e){
                Toast.makeText(this, "User with that username does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
