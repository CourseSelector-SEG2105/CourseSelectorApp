package com.example.courseselector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginBtn, signupBtn;
    EditText username, password;
    CheckBox instructorCheckbox;

    static UserDatabase userDatabase;
    static CourseDatabase courseDatabase;
    static StudentCourseDatabase scDatabase;
    Authentication authenticator = new Authentication();

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
        courseDatabase = new CourseDatabase(this);
        scDatabase = new StudentCourseDatabase(this);

        User adminUser = new User("admin", "admin123", User.ADMIN_ROLE);

        try {
            userDatabase.addUser(adminUser);
            System.out.println("Success");
        } catch (UserExistsException e) {
            System.out.println("Admin setup error, database exists");
        }

        signupBtn.setOnClickListener(v -> {
            if(signup()) {
                login();
            }
        });

        loginBtn.setOnClickListener(v -> login());

    }

    public boolean signup() {
        String usernameString = username.getText().toString();
        String userPassword = password.getText().toString();
        int tmpUserRole = instructorCheckbox.isChecked()? User.INSTRUCTOR_ROLE: User.STUDENT_ROLE;

        User signupUser = new User(usernameString, userPassword, tmpUserRole);
        return authenticator.signup(signupUser, userDatabase, MainActivity.this);
    }

    public void login() {
        String usernameString = username.getText().toString();
        Intent intent;
        if(userDatabase.userExists(usernameString)) {
            User loginUser = new User(usernameString, password.getText().toString());

            if(loginUser.getUsername().equals("admin")) {
                intent = new Intent(MainActivity.this, AdminLoginScreen.class);
            }else if(userDatabase.getRole(loginUser.getUsername()).equals("instructor")) {
                intent = new Intent(MainActivity.this, InstructorLoginScreen.class);
            }else {
                intent = new Intent(MainActivity.this, LoginScreen.class);
            }

            if (authenticator.login(loginUser, userDatabase, MainActivity.this)) {
                intent.putExtra("username", loginUser.getUsername());
                intent.putExtra("role", userDatabase.getRole(loginUser.getUsername()));
                startActivity(intent);
            }

        } else {
            Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
        }
    }
}