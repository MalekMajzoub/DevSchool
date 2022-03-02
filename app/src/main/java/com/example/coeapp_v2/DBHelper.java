package com.example.coeapp_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="proj.db";
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(username TEXT PRIMARY KEY, fname TEXT, lname TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public Boolean insertData(String username, String fname, String lname, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("fname", fname);
        values.put("lname", lname);
        values.put("password", password);

        long result = db.insert("USER", null, values);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username=?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

        public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username=? AND password=?", new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
        }

    public String fetchName(String username){
        String fullname ="hi";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 =db.rawQuery("SELECT fname FROM USER WHERE username=?", new String[]{username});
        if (cursor1.moveToFirst()){
            fullname = cursor1.getString(0);
        }
        Cursor cursor2 =db.rawQuery("SELECT lname FROM USER WHERE username=?", new String[]{username});
        if (cursor2.moveToFirst()){
            fullname+=" "+cursor2.getString(0);
        }
        return fullname;
    }
}