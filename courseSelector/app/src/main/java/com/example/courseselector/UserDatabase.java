package com.example.courseselector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String TABLE_NAME      = "users";
    private static final String COLUMN_ID       = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE     = "role";
    private static final String DATABASE_NAME   = "users.db";
    private static final int DATABASE_VERSION   = 1;



    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " INTEGER )";
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

    public void addUser(User user) throws UserExistsException {

        if(userExists(user.getUsername())) {
            throw new UserExistsException("User already exists");
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getRole());

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public String getPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        if(!userExists(username)) {
            return null;
        }

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "'", null);
        cursor.moveToNext();

        String password = cursor.getString(0);
        cursor.close();
        return password;
    }

    public String getRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ROLE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "'", null);
        cursor.moveToNext();

        String role;
        int roleVal = cursor.getInt(0);

        role = roleVal == User.STUDENT_ROLE ? "student" : roleVal == User.INSTRUCTOR_ROLE ? "instructor" : "admin";

        return role;
    }

    public boolean userExists(String username) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "'", null).getCount() != 0;
    }

    public void deleteUser(String username) throws UserDoesNotExistException{
        if (!userExists(username)){
            throw new UserDoesNotExistException("User does not exist");
        }
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_USERNAME+"=?",new String[]{username});
        database.close();
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null); // returns "cursor" all products from the table
    }


    }


