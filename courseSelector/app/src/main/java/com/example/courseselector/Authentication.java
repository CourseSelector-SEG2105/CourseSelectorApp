package com.example.courseselector;

import android.content.Context;
import android.widget.Toast;

public class Authentication {

    public boolean signup(User user, UserDatabase db, Context context) {

        if(!isValidEmail(user.getUsername())) {
            Toast.makeText(context, "invalid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(user.getPassword())) {
            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                db.addUser(user);
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                return true;
            } catch (UserExistsException e) {
                Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean login(User user, UserDatabase db, Context context) {
        if( user.getPassword().equals(db.getPassword(user.getUsername())) ){
            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show();
            return  true;
        } else {
            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    protected boolean isValidEmail(String email) {
        return (email.length()>0 && email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"));
    }

    protected boolean isValidPassword(String password) {
        return password.length()>0;
    }

}
