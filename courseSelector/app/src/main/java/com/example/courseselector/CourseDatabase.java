package com.example.courseselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CourseDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME      = "CourseDatabase";
    private static final String id              = "id";
    private static final String COURSE_CODE     = "courseCode";
    private static final String COURSE_NAME     = "courseName";
    private static final String DATABASE_NAME   = "CourseDatabase.db";
    private static final int DATABASE_VERSION   = 1;

    CourseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_cmd = "CREATE TABLE " + TABLE_NAME +
        " (" + id + " INTEGER PRIMARY KEY, " +
        COURSE_CODE + " TEXT, " +
        COURSE_NAME + " TEXT)";

        sqLiteDatabase.execSQL(create_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void addCourse(Course course) throws CourseExistsException {

        if(courseExists(course.getCourseName())) {
            throw new CourseExistsException("Course already exists");
        }

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COURSE_NAME, course.getCourseName());
        values.put(COURSE_CODE, course.getCourseCode());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public boolean courseExists(String courseName){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null).getCount() != 0;
    }

    public boolean courseExists2(String courseCode){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null).getCount() != 0;
    }

    public void deleteCourse(String courseCode) throws CourseDoesNotExistException{
        if (!courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Course does not exist");
        }
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COURSE_CODE+"=?",new String[]{courseCode});
        database.close();
    }

    public void editCourse(String oldCourseCode, String newCourseName, String newCourseCode) throws CourseDoesNotExistException{
        if (!courseExists2(oldCourseCode)){
            throw new CourseDoesNotExistException("Course does not exist");
        }
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues updatedCourse= new ContentValues();
        updatedCourse.put(COURSE_CODE, newCourseCode);
        updatedCourse.put(COURSE_NAME, newCourseName);
        database.update(TABLE_NAME,updatedCourse,COURSE_CODE+"=?", new String[]{oldCourseCode});
        database.close();
    }
}
