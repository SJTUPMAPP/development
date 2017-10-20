package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 2017/10/4.
 */

public class TaskActivity {
    private DBHelper dbHelper;

    public TaskActivity(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insert(Task newtask){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String insertQuery = "INSERT INTO " + Task.TABLE
                + " (" +Task.Task_Name +"," + Task.Owner1 +","+ Task.CreateTime + "," + Task.StartDate1 +"," +Task.EndDate1+ "," +Task.Prev_Task
                +"," + Task.Next_Task + "," + Task.Main_Task + ") VALUES ('"
                + newtask.name + "','"+ newtask.owner + "','now', '"+ newtask.startDate +"','"+ newtask.endDate +"','"+ newtask.prevTask +"', '"
                + newtask.nextTask +"', '"+ newtask.mainTask+"')";

        //Inserting Row
        db.execSQL(insertQuery);
        db.close(); //Closing database connection
    }

    public void delete(int task_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Task.TABLE, Task.KEY_ID + "= ?", new String[] { String.valueOf(task_Id) });
        db.close(); // Closing database connection
    }


    public void update(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Task_Name, task.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.KEY_ID + "= ?", new String[] { String.valueOf(task.task_ID) });
        db.close(); // Closing database connection

    }

    public ArrayList<HashMap<String,String>> getTaskList(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Task.KEY_ID + "," +
                Task.Task_Name+
                " FROM " + Task.TABLE;

        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("ID", cursor.getString(cursor.getColumnIndex(Task.KEY_ID)));
                student.put("TaskName", cursor.getString(cursor.getColumnIndex(Task.Task_Name)));
                studentList.add(student);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    public Task getTaskById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Task.KEY_ID + "," +
                Task.Task_Name +
                " FROM " + Task.TABLE +
                " WHERE " + Task.KEY_ID + "=?";
        Task task = new Task();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if(cursor.moveToFirst()){
            do{
                task.task_ID =cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                task.name =cursor.getString(cursor.getColumnIndex(Task.Task_Name));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return task;

    }


}
