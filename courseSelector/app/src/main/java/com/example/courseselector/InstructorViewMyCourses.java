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
import java.util.regex.Pattern;

public class InstructorViewMyCourses extends AppCompatActivity {

    public static CourseDatabase database;
    public static EditText courseCode;
    public static EditText description;
    public static EditText courseDay;
    public static EditText courseStartTime;
    public static EditText courseEndTime;
    public static EditText capacity;

    String instructorUsername;
    Button saveButton;

    ListView courseListView;
    private ArrayList<String> courseList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        instructorUsername = intent.getStringExtra("username");
        database = MainActivity.courseDatabase;
        setContentView(R.layout.instructor_courses_edit_layout);

//        Variables
        courseCode = findViewById(R.id.course_code_field);
        description = findViewById(R.id.editDesc);
        courseDay = findViewById(R.id.editDay);
        courseStartTime = findViewById(R.id.editCourseStartTime);
        courseEndTime = findViewById(R.id.editCourseEndTime);
        capacity = findViewById(R.id.editCapacity);
        saveButton = findViewById(R.id.saveButton);
        courseListView = findViewById(R.id.list_of_courses);
        courseList = new ArrayList<>();

        viewCourses();

        saveButton.setOnClickListener(v -> {
            try {

                int capacityTmp = 0;
                if(capacity.getText().length() > 0) {
                    capacityTmp = Integer.parseInt(capacity.getText().toString());
                }

                String courseDayProcessed = courseDay.getText().toString().substring(0, 3);

                if(courseDay.length() > 0) {
                    if(courseDayProcessed.matches("(?i)mon|tue|wed|thu|fri|sat|sun")) {
                        database.instructorEditCourse(courseCode.getText().toString(), description.getText().toString(), courseDayProcessed, courseStartTime.getText().toString(), courseEndTime.getText().toString(), capacityTmp);
                        viewCourses();
                        Toast.makeText(this, "Course changes have been successfully saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Enter a valid day of week", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    database.instructorEditCourse(courseCode.getText().toString(), description.getText().toString(), courseDayProcessed, courseStartTime.getText().toString(), courseEndTime.getText().toString(), capacityTmp);
                    viewCourses();
                    Toast.makeText(this, "Course changes have been successfully saved", Toast.LENGTH_SHORT).show();
                }

            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Course with that code does not exist", Toast.LENGTH_SHORT).show();
            }catch (NumberFormatException e){
                Toast.makeText(this, "Capacity should be a positive integer", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
              Toast.makeText(this, "Enter a day of the week/Capacity", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void viewCourses() {
        courseList.clear();
        Cursor cursor = database.getSpecificData(instructorUsername);
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                courseList.add(course.toString2());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }



}
