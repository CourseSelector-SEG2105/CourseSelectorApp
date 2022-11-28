package com.example.courseselector;

import static java.sql.Types.NULL;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class CourseDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME      = "CourseDatabase";
    private static final String id              = "id";
    private static final String COURSE_CODE     = "courseCode";
    private static final String COURSE_NAME     = "courseName";


//    Added new columns
    private static final String COURSE_DESCRIPTION = "courseDescription";
    private static final String COURSE_DAY = "courseDay";
    private static final String COURSE_START_TIME = "courseStartTime";
    private static final String COURSE_END_TIME = "courseEndTime";
    private static final String COURSE_CAPACITY = "courseCapacity";
    private static final String COURSE_INSTRUCTOR = "courseInstructor";

//    database things
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
        COURSE_NAME + " TEXT, " +
        COURSE_INSTRUCTOR + " TEXT, " +
        COURSE_DESCRIPTION + " TEXT, " +
        COURSE_DAY + " TEXT, " +
        COURSE_START_TIME+ " TEXT, " +
        COURSE_END_TIME + " TEXT, " +
        COURSE_CAPACITY + " INTEGER ) ";

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

    public Cursor getSpecificData(String username){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_INSTRUCTOR + " = '" + username + "'" , null);
    }

    public void addCourse(Course course) throws CourseExistsException {

        if(courseExists(course.getCourseName())) {
            throw new CourseExistsException("Course already exists");
        }

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COURSE_NAME, course.getCourseName());
        values.put(COURSE_CODE, course.getCourseCode());
        values.put(COURSE_INSTRUCTOR, course.getCourseInstructor());
        values.put(COURSE_DESCRIPTION, course.getCourseDescription());
        values.put(COURSE_DAY, course.getCourseTiming());
        values.put(COURSE_START_TIME, course.getCourseStartTime());
        values.put(COURSE_END_TIME, course.getCourseEndTime());
        values.put(COURSE_CAPACITY, course.getCourseCapacity());


        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public String getInstructor2(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(3);
    }

    public String getCourseCode(String courseName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    public String getCourseName(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    public String getCourseDay(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(5);
    }

    public String getCourseDay2(String courseName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(5);
    }


    public String getCourseStartTime(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(6);
    }

    public String getCourseStartTime2(String courseName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(6);
    }

    public String getCourseEndTime(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(7);
    }

    public String getCourseEndTime2(String courseName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(7);
    }



    public boolean courseExists(String courseName){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_NAME + " = '" + courseName + "'", null).getCount() != 0;
    }

    public boolean courseExists2(String courseCode){
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COURSE_CODE + " = '" + courseCode + "'", null).getCount() != 0;
    }

    public String instructorExists(String courseCode){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);
        cursor.moveToFirst();
        return cursor.getString(3);
    }

    public String instructorExists2(String courseName){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COURSE_NAME + " = '" + courseName + "'", null);
        cursor.moveToFirst();
        return cursor.getString(3);
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

    public int getCapacityFromName(String courseName) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT " + COURSE_CAPACITY + " FROM " + TABLE_NAME +" WHERE " + COURSE_NAME + " = '" + courseName + "'", null);

        if(cursor.getCount() == 0) {
            return -1;
        }

        cursor.moveToNext();

        int returnInt = cursor.getInt(0);
        cursor.close();
        return returnInt;
    }

    public int getCapacityFromCode(String courseCode) {
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT " + COURSE_CAPACITY + " FROM " + TABLE_NAME +" WHERE " + COURSE_CODE + " = '" + courseCode + "'", null);

        if(cursor.getCount() == 0) {
            return -1;
        }

        cursor.moveToNext();

        int returnInt = cursor.getInt(0);
        cursor.close();
        return returnInt;
    }


    public void instructorEditCourse(String courseCode, String courseDesc, String courseDay, String courseStartTime, String courseEndTime, int courseCapacity) throws CourseDoesNotExistException, NumberFormatException, Exception{
        if (!courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Course does not exist");
        }

        int currentCapacity = this.getCapacityFromCode(courseCode);

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues updatedCourse= new ContentValues();

        if(courseDesc.length() > 0){
            updatedCourse.put(COURSE_DESCRIPTION, courseDesc);
        }

        if(courseDay.length()>0){
            updatedCourse.put(COURSE_DAY, courseDay);
        }

        if(courseStartTime.length()>0){
            updatedCourse.put(COURSE_START_TIME, courseStartTime);
        }

        if(courseEndTime.length()>0){
            updatedCourse.put(COURSE_END_TIME, courseEndTime);
        }

        if(courseCapacity > 0){
            updatedCourse.put(COURSE_CAPACITY, courseCapacity);
        } else {
            if(currentCapacity == -1) {
                throw new Exception();
            }
        }

        database.update(TABLE_NAME,updatedCourse,COURSE_CODE+"=?", new String[]{courseCode});
        database.close();
    }

    public void addInstructor(String courseCode, String username) throws InstructorExistsException, CourseDoesNotExistException{

        if(!courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Error");
        }

        if(!instructorExists(courseCode).equals("NA")){
            throw new InstructorExistsException("Error");
        }

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues addInstructor = new ContentValues();
        addInstructor.put(COURSE_INSTRUCTOR, username);
        database.update(TABLE_NAME,addInstructor,COURSE_CODE+"=?", new String[]{courseCode});
        database.close();
    }

    public void addInstructor2(String courseName, String username) throws InstructorExistsException, CourseDoesNotExistException{

        if(!courseExists(courseName)){
            throw new CourseDoesNotExistException("Error");
        }

        if(!instructorExists2(courseName).equals("NA")){
            throw new InstructorExistsException("Error");
        }

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues addInstructor = new ContentValues();
        addInstructor.put(COURSE_INSTRUCTOR, username);
        database.update(TABLE_NAME,addInstructor,COURSE_NAME+"=?", new String[]{courseName});
        database.close();
    }

    public void removeInstructor(String courseCode, String username) throws InstructorDoesNotExistException, CourseDoesNotExistException{

        if(!courseExists2(courseCode)){
            throw new CourseDoesNotExistException("Error");
        }

        if(!instructorExists(courseCode).equals(username)){
            throw new InstructorDoesNotExistException("Error");
        }

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues removeInstructor = new ContentValues();
        removeInstructor.put(COURSE_INSTRUCTOR, "NA");
        removeInstructor.put(COURSE_CAPACITY, 0);
        removeInstructor.put(COURSE_DESCRIPTION, "NA");
        removeInstructor.put(COURSE_DAY, "NA");
        removeInstructor.put(COURSE_START_TIME, "NA");
        removeInstructor.put(COURSE_END_TIME, "NA");
        database.update(TABLE_NAME,removeInstructor,COURSE_CODE+"=?", new String[]{courseCode});
        database.close();
    }

    public void removeInstructor2(String courseName, String username) throws InstructorDoesNotExistException, CourseDoesNotExistException{

        if(!courseExists(courseName)){
            throw new CourseDoesNotExistException("Error");
        }

        if(!instructorExists2(courseName).equals(username)){
            throw new InstructorDoesNotExistException("Error");
        }

        
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues removeInstructor = new ContentValues();

        removeInstructor.put(COURSE_INSTRUCTOR, "NA");
        removeInstructor.put(COURSE_CAPACITY, 0);
        removeInstructor.put(COURSE_DESCRIPTION, "NA");
        removeInstructor.put(COURSE_DAY, "NA");
        removeInstructor.put(COURSE_START_TIME, "NA");
        removeInstructor.put(COURSE_END_TIME, "NA");

        database.update(TABLE_NAME,removeInstructor,COURSE_NAME+"=?", new String[]{courseName});
        database.close();
    }

}
