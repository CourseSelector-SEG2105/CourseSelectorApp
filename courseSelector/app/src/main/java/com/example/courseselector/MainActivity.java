package com.example.courseselector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, signupBtn;
    EditText username, password;
    CheckBox instructorCheckbox;

    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);

        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.signup_btn);

        instructorCheckbox = findViewById(R.id.instructorAccCheck);

        userDatabase = new UserDatabase(this);

        User adminUser = new User("admin", "admin123", User.ADMIN_ROLE);

        try {
            userDatabase.addUser(adminUser);
            System.out.println("Success");
        } catch (UserExistsException e) {
            System.out.println("Admin setup error");
        }

        signupBtn.setOnClickListener(v -> {
            signup();
            login();
        });

        loginBtn.setOnClickListener(v -> {
            login();
        });

    }

    public void signup() {
        String usernameString = username.getText().toString();
        String userPassword = password.getText().toString();

        if(!isValidEmail(usernameString)) {
            Toast.makeText(MainActivity.this, "invalid email", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(userPassword)) {
            Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
        } else {
            int tmpUserRole = instructorCheckbox.isChecked()? User.INSTRUCTOR_ROLE: User.STUDENT_ROLE;
            User tmpUser = new User(usernameString, userPassword, tmpUserRole);

            try {
                userDatabase.addUser(tmpUser);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (UserExistsException e) {
                Toast.makeText(MainActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login() {
        String userPassword, userRole;
        String usernameString = username.getText().toString();

        try {
            userPassword = userDatabase.getPassword(usernameString);

            if(userPassword.equals(password.getText().toString()) && !usernameString.equals("admin")) {
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                userRole = userDatabase.getRole(usernameString);
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                intent.putExtra("username", usernameString);
                intent.putExtra("role", userRole);
                startActivity(intent);
            } else if(userPassword.equals(password.getText().toString())){
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                userRole = userDatabase.getRole(usernameString);
                Intent intent = new Intent(MainActivity.this, AdminLoginScreen.class);
                intent.putExtra("username", usernameString);
                intent.putExtra("role", userRole);
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        } catch (UserDoesNotExistException e) {
            Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidEmail(String email) {
        return (email.length()>0 && email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"));
    }

    public boolean isValidPassword(String password) {
        return password.length()>0;
    }
}