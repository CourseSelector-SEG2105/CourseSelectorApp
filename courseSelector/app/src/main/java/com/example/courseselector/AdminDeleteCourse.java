package com.example.courseselector;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminDeleteCourse extends AppCompatActivity {

    private static CourseDatabase database;
    private EditText courseCode;
    Button deleteCourse;

    ListView courseListView;
    private ArrayList<String> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_delete_layout);

        database = MainActivity.courseDatabase;
        courseCode = findViewById(R.id.course_code_field);
        deleteCourse = findViewById(R.id.deleteCourseBtn);

        courseListView = findViewById(R.id.list_of_courses);
        courseList = new ArrayList<>();

        viewCourses();

        deleteCourse.setOnClickListener(v -> {
            try {
                database.deleteCourse(courseCode.getText().toString());
                viewCourses();
                Toast.makeText(this, "Course successfully deleted", Toast.LENGTH_SHORT).show();
            } catch (CourseDoesNotExistException e){
                Toast.makeText(this, "Course with that code does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewCourses() {
        courseList.clear();
        Cursor cursor = database.getData();

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                Course course = new Course(cursor.getString(1), cursor.getString(2));
                courseList.add(course.toString());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }

}
