package com.example.courseselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class StudentCourseDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "StudentCourseDatabase";
    private static final String id = "id";
    private static final String STUDENT_NAME = "studentName";
    private static final String COURSE_CODE = "courseCode";
    private static final String COURSE_NAME = "courseName";

    public CourseDatabase courseDatabase = MainActivity.courseDatabase;

    private static final String DATABASE_NAME = "StudentCourseDatabase.db";
    private static final int DATABASE_VERSION = 1;

    StudentCourseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_cmd = "CREATE TABLE " + TABLE_NAME +
                " (" + id + " INTEGER PRIMARY KEY, " +
                STUDENT_NAME + " TEXT, " +
                COURSE_CODE + " TEXT," +
                COURSE_NAME + " TEXT )";

        sqLiteDatabase.execSQL(create_cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getData() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

//    public String[] getCourseTime(String courseCode) {
//        CourseDatabase courseDatabase = MainActivity.courseDatabase;
//        Cursor cursor = courseDatabase.getCoursesFromCode(courseCode);
//        String[] returnString = {"NA", "NA"};
//
//        if (cursor.getCount() == 0) {
//            return returnString;
//        }
//        returnString[0] = cursor.getString(6);
//        returnString[1] = cursor.getString(7);
//        return returnString;
//    }

    public int getOccupancyFromName(String courseName) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        int occupancy = cursor.getCount();
        cursor.close();
        return occupancy;
    }


    public int getOccupancyFromCode(String courseCode) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        int occupancy = cursor.getCount();
        cursor.close();
        return occupancy;
    }

    public Cursor getEnrolledCourses(String username) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + STUDENT_NAME + " = '" + username + "'", null);
    }

    public void unenrollStudent(String courseCode, String username) throws CourseDoesNotExistException{
        if(!courseDatabase.courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Error");
        }

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COURSE_CODE + "=? AND " + STUDENT_NAME + "=?", new String[]{courseCode, username});
        database.close();
    }

    public void unenrollStudent2(String courseName, String username) throws CourseDoesNotExistException {
        if(!courseDatabase.courseExists(courseName)){
            throw new CourseDoesNotExistException("Error");
        }
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COURSE_NAME + "=? AND " + STUDENT_NAME + "=?", new String[]{courseName, username});
        database.close();
    }

    public Boolean alreadyEnrolled(String username, String coursecode){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " =? AND " + STUDENT_NAME + " =? ", new String[]{coursecode, username}).getCount() !=0;
    }

    public Boolean alreadyEnrolled2(String username, String courseName){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " =? AND " + STUDENT_NAME + " =? ", new String[]{courseName, username}).getCount() !=0;
    }


    public void enrollStudent(String courseCode, String username) throws CourseDoesNotExistException, TimeConflictException, StudentAlreadyEnrolled {
        if(!courseDatabase.courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Error");
        }

        if (alreadyEnrolled(username, courseCode)){
            throw new StudentAlreadyEnrolled("Error");
        }

        int occupancy = getOccupancyFromCode(courseCode);
        int capacity = courseDatabase.getCapacityFromCode(courseCode);

        System.out.println(occupancy + " " + capacity);

        if(occupancy >= capacity) {
            throw new IndexOutOfBoundsException();
        }

        if(courseDatabase.getCourseStartTime(courseCode).equals("NA") || courseDatabase.getCourseEndTime(courseCode).equals("NA")){
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COURSE_CODE, courseCode);
            values.put(COURSE_NAME, courseDatabase.getCourseName(courseCode));
            values.put(STUDENT_NAME, username);
            database.insert(TABLE_NAME, null, values);
            database.close();
        } else {
            int start = Integer.parseInt(courseDatabase.getCourseStartTime(courseCode).replaceAll("[^\\d.]", ""));
            int end =  Integer.parseInt(courseDatabase.getCourseEndTime(courseCode).replaceAll("[^\\d.]", ""));

            if (canEnroll(username, courseDatabase.getCourseDay(courseCode),start, end)){
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COURSE_CODE, courseCode);
                values.put(COURSE_NAME, courseDatabase.getCourseName(courseCode));
                values.put(STUDENT_NAME, username);
                database.insert(TABLE_NAME, null, values);
                database.close();
            } else {
                throw new TimeConflictException("Error");
            }
        }


    }

    public void enrollStudent2(String courseName, String username) throws CourseDoesNotExistException, TimeConflictException, StudentAlreadyEnrolled, IndexOutOfBoundsException {

        if(!courseDatabase.courseExists(courseName)){
            throw new CourseDoesNotExistException("Error");
        }

        if (alreadyEnrolled2(username, courseName)){
            throw new StudentAlreadyEnrolled("Error");
        }

        int occupancy = getOccupancyFromName(courseName);
        int capacity = courseDatabase.getCapacityFromName(courseName);

        System.out.println(occupancy + " " + capacity);

        if(occupancy >= capacity) {
            throw new IndexOutOfBoundsException();
        }

        if(courseDatabase.getCourseStartTime2(courseName).equals("NA") || courseDatabase.getCourseEndTime2(courseName).equals("NA")){
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COURSE_NAME, courseName);
            values.put(COURSE_CODE, courseDatabase.getCourseCode(courseName));
            values.put(STUDENT_NAME, username);
            database.insert(TABLE_NAME, null, values);
            database.close();
        } else {
            int start = Integer.parseInt(courseDatabase.getCourseStartTime2(courseName).replaceAll("[^\\d.]", ""));
            int end =  Integer.parseInt(courseDatabase.getCourseEndTime2(courseName).replaceAll("[^\\d.]", ""));

            if (canEnroll(username, courseDatabase.getCourseDay2(courseName),start, end)){
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COURSE_NAME, courseName);
                values.put(COURSE_CODE, courseDatabase.getCourseCode(courseName));
                values.put(STUDENT_NAME, username);
                database.insert(TABLE_NAME, null, values);
                database.close();
            }else {
                throw new TimeConflictException("Error");
            }
        }

    }

    public Boolean canEnroll(String username, String courseDay, int courseStart, int courseEnd) {
        int start;
        int end;
        Cursor cursor = this.getEnrolledCourses(username);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            while (cursor.moveToNext()) {
                if (courseDatabase.getCourseDay(cursor.getString(2)).equals(courseDay)) {
                    start = Integer.parseInt(courseDatabase.getCourseStartTime(cursor.getString(2)).replaceAll("[^\\d.]", ""));
                    end = Integer.parseInt(courseDatabase.getCourseEndTime(cursor.getString(2)).replaceAll("[^\\d.]", ""));
                    return !((start <= courseStart && courseStart <= courseEnd) || (start <= courseEnd && end >= courseEnd));
                }
            }
        }

        return true;

    }

}
