package com.glsi.chatify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Database name and version
    private static final String DATABASE_NAME = "chatify_db";
    private static final int DATABASE_VERSION = 1;

    // User table name and columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // SQL query to create the users table
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FULL_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_PASSWORD + " TEXT"
            + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table when the database is created
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to add a new user
    public boolean addUser(User user) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_FULL_NAME, user.getFullName());
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_PASSWORD, user.getPassword());

            // Insert the user into the database
            long result = db.insert(TABLE_USERS, null, values);

            // Return true if the insertion was successful, otherwise false
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        } finally {
            if (db != null) {
                db.close(); // Ensure the database connection is closed
            }
        }
    }


    // Method to retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return users;
    }

    // Method to get a user by email and password (for login)
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean isValidUser = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isValidUser;
    }

    // Method to delete a user by ID
    public boolean deleteUser(int userId) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Deleting the user from the database
            int rowsAffected = db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});

            // Return true if the user was deleted (rowsAffected > 0), otherwise false
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        } finally {
            if (db != null) {
                db.close(); // Ensure the database connection is closed
            }
        }
    }


    // Method to update a user's details
    public boolean updateUser(User user) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_FULL_NAME, user.getFullName());
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_PASSWORD, user.getPassword());

            // Updating the user's details
            int rowsAffected = db.update(TABLE_USERS, values, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});

            // Return true if the update was successful (rowsAffected > 0), otherwise false
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        } finally {
            if (db != null) {
                db.close(); // Ensure the database connection is closed
            }
        }
    }

}
