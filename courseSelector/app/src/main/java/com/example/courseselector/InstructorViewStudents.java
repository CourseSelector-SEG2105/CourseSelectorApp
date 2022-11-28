package com.example.courseselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstructorViewStudents extends AppCompatActivity {

    public static CourseDatabase database;
    public EditText username;

    Button searchButton;
    public static StudentCourseDatabase scDatabase;
    public static UserDatabase userDatabase;
    public static EditText courseCode;
    public static EditText courseName;
    ListView userListView;
    String instructorUsername;
    ArrayList<String> userList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        instructorUsername=intent.getStringExtra("username");
        setContentView(R.layout.instructor_students_view);
        scDatabase = MainActivity.scDatabase;
        userDatabase=MainActivity.userDatabase;
        database= MainActivity.courseDatabase;
        searchButton = findViewById(R.id.viewStudentSearch);
        courseCode = findViewById(R.id.courseCodeText);
        courseName = findViewById(R.id.courseNameText);
        userListView = findViewById(R.id.listOfUsers);
        userList = new ArrayList<>();

        searchButton.setOnClickListener(v -> {
            if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("")){
                Toast.makeText(this, "Course with that code/name does not exist", Toast.LENGTH_SHORT).show();
            } else if(courseName.getText().toString().matches("") && !courseCode.getText().toString().matches("")) {
                findByCode(courseCode.getText().toString());
            } else if(courseCode.getText().toString().matches("") && !courseName.getText().toString().matches("") ) {
                findByCode(database.getCourseCode(courseName.getText().toString()));
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void findByCode(String courseCode){
        userList.clear();
        Cursor cursor = scDatabase.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(2).equals(courseCode)) {
                    if ( database.getInstructor2(cursor.getString(2)).equals(instructorUsername)){
                        User user = new User(cursor.getString(1),userDatabase.getPassword(cursor.getString(1)),0);
                        userList.add(user.toString());
                    }
                }
            }
        }

        if(userList.isEmpty()){
            Toast.makeText(this, "Either you have no students or you don't teach this course", Toast.LENGTH_SHORT).show();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        userListView.setAdapter(adapter);

    }

//    idk why this one did not work, but i found a diff solution
    public void findByName(String courseName){
        userList.clear();
        Cursor cursor = database.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(3).equals(courseName)) {
                    if (database.getInstructor2(cursor.getString(2)).equals(instructorUsername)){
                        User user = new User(cursor.getString(1),userDatabase.getPassword(cursor.getString(1)),0);
                        userList.add(user.toString());
                    }
                }
            }
        }

        if(userList.isEmpty()){
            Toast.makeText(this, "Either you have no students or you don't teach this course", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        userListView.setAdapter(adapter);

    }
}


