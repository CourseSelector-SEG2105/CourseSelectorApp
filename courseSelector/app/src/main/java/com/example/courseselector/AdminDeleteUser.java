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

public class AdminDeleteUser extends AppCompatActivity {

    public static UserDatabase database = MainActivity.userDatabase;
    public EditText username;

    Button deleteUserBtn;
    ListView userListView;
    ArrayList<String> userList;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_delete_layout);

        username = findViewById(R.id.usernameField);
        deleteUserBtn = findViewById(R.id.delete_user_button);

        userListView = findViewById(R.id.listOfUsers);
        userList = new ArrayList<>();

        viewUsers();

        deleteUserBtn.setOnClickListener(v -> {
            try {
                if(!username.getText().toString().equals("admin")) {
                    database.deleteUser(username.getText().toString());
                    Toast.makeText(this, "User successfully deleted", Toast.LENGTH_SHORT).show();
                    viewUsers();
                } else {
                    Toast.makeText(this, "Cannot delete admin", Toast.LENGTH_LONG).show();
                }
            } catch (UserDoesNotExistException e){
                Toast.makeText(this, "User with that username does not exist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewUsers() {
        userList.clear();
        Cursor cursor = database.getData();

        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                User user = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                userList.add(user.toString());
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        userListView.setAdapter(adapter);
    }


}
