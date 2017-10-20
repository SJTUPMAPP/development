package com.example.myfirstapp;

/**
 * Created by User on 2017/10/4.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "task.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Task.TABLE  + "("
                + Task.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Task.Task_Name + " TEXT, "
                + Task.Prev_Task + " TEXT, "
                + Task.Next_Task + " TEXT, "
                + Task.Main_Task + " TEXT, "
                + Task.Rank + " INTEGER, "
                + Task.Level + " INTEGER, "
                + Task.X + " INTEGER, "
                + Task.Y + " INTEGER, "
                + Task.Owner1 + " TEXT, "
                + Task.Owner2 + " TEXT, "
                + Task.Owner3 + " TEXT, "
                + Task.Status + " TEXT, "
                + Task.CreateTime + " TEXT, "
                + Task.StartDate1 + " TEXT, "
                + Task.EndDate1 + " TEXT, "
                + Task.AlterTime1 + " TEXT, "
                + Task.StartDate2 + " TEXT, "
                + Task.EndDate2 + " TEXT, "
                + Task.AlterTime2 + " TEXT, "
                + Task.StartDate3 + " TEXT, "
                + Task.EndDate3 + " TEXT, "
                + Task.Comment + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE);

        // Create tables again
        onCreate(db);

    }

}