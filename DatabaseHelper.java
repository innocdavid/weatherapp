package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "registers.db";
    public static final String TABLE_NAME = "Register.db";
    public static final String COL_1 = "FirstName";
    public static final String COL_2 = "Username";
    public static final String COL_3 = "Email";
    public static final String COL_4 = "Password";
    //public static  final String COL_5="register.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Register(FirstName TEXT, Username TEXT,Email TEXT,Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long addUseer(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put("Username", user);
        contentValue.put("Password", password);
        Long rest = db.insert("register", null, contentValue);
        return rest;
    }

    public boolean checkUser(String Username, String Password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = {Username, Password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0)
            return true;
        else
            return false;

    }
}