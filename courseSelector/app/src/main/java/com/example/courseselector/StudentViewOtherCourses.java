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

public class StudentViewOtherCourses extends AppCompatActivity {

    public static CourseDatabase database;
    public static StudentCourseDatabase scDatabase;
    ListView courseListView;
    private ArrayList<String> courseList;
    public static EditText courseCode, courseName, courseDay;
    Button searchButton, enrollButton;
    String studentUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        studentUsername = intent.getStringExtra("username");
        database = MainActivity.courseDatabase;
        scDatabase = MainActivity.scDatabase;
        setContentView(R.layout.student_courses_enroll);
        courseCode = findViewById(R.id.course_code_field);
        courseName=findViewById(R.id.courseName);
        courseDay = findViewById(R.id.courseDay);
        searchButton = findViewById(R.id.searchButton);
        enrollButton = findViewById(R.id.student_enroll);
        courseListView = findViewById(R.id.list_of_courses);
        courseList = new ArrayList<>();
        viewCourses();
//
        searchButton.setOnClickListener(v -> {

           if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("") && courseDay.getText().toString().matches("")){
              viewCourses();
              Toast.makeText(this, "Course with that code/name does not exist", Toast.LENGTH_SHORT).show();
           } else if(courseName.getText().toString().matches("") && !courseCode.getText().toString().matches("")) {
               findCourseByCode(courseCode.getText().toString() );
           } else if(courseCode.getText().toString().matches("") && !courseName.getText().toString().matches("") ) {
               findCourseByName(courseName.getText().toString());
           } else {
               findCourseByDay(courseDay.getText().toString());
           }
        });

        enrollButton.setOnClickListener(v -> {
            if (courseName.getText().toString().matches("") && courseCode.getText().toString().matches("")){
                viewCourses();
                Toast.makeText(this, "Course with that code/name does not exist", Toast.LENGTH_SHORT).show();
            } else if(courseCode.getText().toString().matches("")) {
                try {
                    scDatabase.enrollStudent2(courseName.getText().toString(), studentUsername);
                    Toast.makeText(this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                } catch (CourseDoesNotExistException e) {
                    Toast.makeText(this, "Course with that name does not exist", Toast.LENGTH_SHORT).show();
                } catch (TimeConflictException e) {
                    Toast.makeText(this, "There is a time conflict with that course", Toast.LENGTH_SHORT).show();
                } catch(StudentAlreadyEnrolled e){
                    Toast.makeText(this, "Student already enrolled in this course", Toast.LENGTH_SHORT).show();
                } catch(IndexOutOfBoundsException e) {
                    Toast.makeText(this, "Maximum capacity reached", Toast.LENGTH_SHORT).show();
                }

            } else if (courseName.getText().toString().matches("")) {
                try {
                    scDatabase.enrollStudent(courseCode.getText().toString(), studentUsername);
                    Toast.makeText(this, "Successfully enrolled", Toast.LENGTH_SHORT).show();
                } catch (CourseDoesNotExistException e) {
                    Toast.makeText(this, "Course with that code does not exist", Toast.LENGTH_SHORT).show();
                } catch (TimeConflictException e) {
                    Toast.makeText(this, "There is a time conflict with that course", Toast.LENGTH_SHORT).show();
                } catch(StudentAlreadyEnrolled e){
                    Toast.makeText(this, "Student already enrolled in this course", Toast.LENGTH_SHORT).show();
                } catch(IndexOutOfBoundsException e) {
                    Toast.makeText(this, "Maximum capacity reached", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Error; please enter course name or code", Toast.LENGTH_SHORT).show();
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
                Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                courseList.add(course.toString3());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }


   private void findCourseByName(String courseName) {
       courseList.clear();
       Cursor cursor = database.getData();
       if (cursor.getCount() == 0) {
           Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
       } else {
           while (cursor.moveToNext()) {
               if(cursor.getString(2).equals(courseName)) {
                   Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                   courseList.add(course.toString3());
               }
           }
       }

       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
       courseListView.setAdapter(adapter);
   }

   private void findCourseByCode(String courseCode) {
       courseList.clear();
       Cursor cursor = database.getData();
       if (cursor.getCount() == 0) {
           Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
       } else {
           while (cursor.moveToNext()) {
               if(cursor.getString(1).equals(courseCode)) {
                   Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                   courseList.add(course.toString3());
               }
           }
       }

       ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
       courseListView.setAdapter(adapter);
   }

    private void findCourseByDay(String courseDay) {
        courseList.clear();
        Cursor cursor = database.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(5).equals(courseDay)) {
                    Course course = new Course(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8));
                    courseList.add(course.toString3());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(adapter);
    }

}
