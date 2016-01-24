package com.example.andrey.myfragment.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table myMesId ("
                + "id integer primary key autoincrement,"
                + "key text" + ");");



        db.execSQL("create table myMes ("
                + "id integer primary key autoincrement,"
                + "time text,"
                + "user text,"
                + "text text" + ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
