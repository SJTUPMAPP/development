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
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {task.name});
        db.close(); // Closing database connection

    }

    public void updateRow(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.X, task.row);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {task.name});
        db.close(); // Closing database connection

    }

    public void updateLocation(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.X, task.row);
        values.put(Task.Y, task.column);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {task.name});
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

    //Find the row of previous task
    public int findPrevRow(String prevTask){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int row = 0;
        String selectQuery = "SELECT " +  Task.X + " FROM " + Task.TABLE + " WHERE TaskName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{prevTask});
        if(cursor.moveToFirst()){
            do{
                row = cursor.getInt(cursor.getColumnIndex(Task.X));
            }while(cursor.moveToNext());
        }
        return row;
    }

    //Find the row of next task
    public int findNextRow(String nextTask){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int row = 0;
        String selectQuery = "SELECT " +  Task.X + " FROM " + Task.TABLE + " WHERE TaskName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nextTask});
        if(cursor.moveToFirst()){
            do{
                row = cursor.getInt(cursor.getColumnIndex(Task.X));
            }while(cursor.moveToNext());
        }
        return row;
    }

    public int findMaxColumn(int layer, int row){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int maxColumn = 0;
        int tempColumn;
        String selectQuery = "SELECT " + Task.Y + " FROM " + Task.TABLE + " WHERE Layer = ? AND X = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(layer), String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                tempColumn = cursor.getInt(cursor.getColumnIndex(Task.Y));
                if (tempColumn >= maxColumn){
                    maxColumn = tempColumn;
                }
            }while(cursor.moveToNext());
        }
        return maxColumn;
    }

    public void LowerRow(int layer, int row){
        Task newtask = new Task();
        int id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Task.X + " FROM " + Task.TABLE + " WHERE Layer = ? AND X >= ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(layer), String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask = getTaskById(id);
                newtask.row = newtask.row + 1;
                updateRow(newtask);

            }while(cursor.moveToNext());
        }
    }


}
