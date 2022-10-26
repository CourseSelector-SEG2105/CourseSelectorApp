package com.example.courseselector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
