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

public class EmployeeActivity {
    private DBHelper dbHelper;

    public EmployeeActivity(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insert(Employee person){
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insertQuery = "INSERT INTO " + Employee.TABLE + " (" +
                Employee.Employee_Name +"," + Employee.Title +","+ Employee.Office + "," +
                Employee.Department +"," + Employee.Team+ "," + Employee.Email +"," +
                Employee.Mobile + "," + Employee.Phone + ","
                +") VALUES ('" +
                person.name + "','"+ person.title +"','"+ person.office +"','"+
                person.department +"','"+ person.team +"', '" + person.email +"', '"+
                person.mobile+"',"+ person.phone +")";

        //Inserting Row
        db.execSQL(insertQuery);
        db.close(); //Closing database connection
    }

    public void delete(int task_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Employee.TABLE, Employee.KEY_ID + "= ?", new String[] { String.valueOf(task_Id) });
        db.close(); // Closing database connection
    }


    public void update(Employee task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.Employee_Name, task.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {task.name});
        db.close(); // Closing database connection

    }

    public void updateRow(Employee task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.X, task.row);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {task.name});
        db.close(); // Closing database connection

    }

    public void updateLocation(Employee task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.X, task.row);
        values.put(Employee.Y, task.column);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {task.name});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String,String>> getEmployeeList(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Employee.KEY_ID + "," +
                Employee.Employee_Name+
                " FROM " + Employee.TABLE;

        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("ID", cursor.getString(cursor.getColumnIndex(Employee.KEY_ID)));
                student.put("EmployeeName", cursor.getString(cursor.getColumnIndex(Employee.Employee_Name)));
                studentList.add(student);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    public Employee getEmployeeById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Employee.KEY_ID + "," +
                Employee.Employee_Name +
                " FROM " + Employee.TABLE +
                " WHERE " + Employee.KEY_ID + "=?";
        Employee person = new Employee();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if(cursor.moveToFirst()){
            do{
                person.empolyee_ID =cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
                person.name =cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return person;

    }

    //Find the row of previous task
    public int findPrevRow(String prevEmployee){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int row = 0;
        String selectQuery = "SELECT " +  Employee.X + " FROM " + Employee.TABLE + " WHERE EmployeeName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{prevEmployee});
        if(cursor.moveToFirst()){
            do{
                row = cursor.getInt(cursor.getColumnIndex(Employee.X));
            }while(cursor.moveToNext());
        }
        return row;
    }

    //Find the row of next task
    public int findNextRow(String nextEmployee){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int row = 0;
        String selectQuery = "SELECT " +  Employee.X + " FROM " + Employee.TABLE + " WHERE EmployeeName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nextEmployee});
        if(cursor.moveToFirst()){
            do{
                row = cursor.getInt(cursor.getColumnIndex(Employee.X));
            }while(cursor.moveToNext());
        }
        return row;
    }

    public int findMaxColumn(int level, int row){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int maxColumn = 0;
        int tempColumn;
        String selectQuery = "SELECT " + Employee.Y + " FROM " + Employee.TABLE + " WHERE Level = ? AND X = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level), String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                tempColumn = cursor.getInt(cursor.getColumnIndex(Employee.Y));
                if (tempColumn >= maxColumn){
                    maxColumn = tempColumn;
                }
            }while(cursor.moveToNext());
        }
        return maxColumn;
    }

    //Increase the value of the row, when its row >= row
    public void LowerRow(int level, int row){
        Employee person = new Employee();
        int id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Employee.X + " FROM " + Employee.TABLE + " WHERE Level = ? AND X >= ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level), String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
                person = getEmployeeById(id);
                person.row = person.row + 1;
                updateRow(person);

            }while(cursor.moveToNext());
        }
    }

    public ArrayList<String> getEmployeeNameList(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Employee.KEY_ID + "," +
                Employee.Employee_Name+
                " FROM " + Employee.TABLE;

        ArrayList<String> taskList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                String task;
                task = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
                taskList.add(task);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

//    public ArrayList<Employee> getActionList(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT " +
//                Employee.KEY_ID + "," +
//                Employee.Employee_Name +"," +
//                Employee.Owner1 + "," +
//                Employee.StartDate1 +"," +
//                Employee.EndDate1 + ","+
//                Employee.Status +","+
//                Employee.Comment +
//                " FROM " +Employee.TABLE;
//        ArrayList<Employee> taskList = new ArrayList<Employee>();
//
//        Cursor cursor = db.rawQuery(selectQuery,null);
//
//        if(cursor.moveToFirst()){
//            do{
//                Employee person = new Employee();
//                person.task_ID = cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
//                person.name = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
//                person.owner = cursor.getString(cursor.getColumnIndex(Employee.Owner1));
//                person.startDate = cursor.getString(cursor.getColumnIndex(Employee.StartDate1));
//                person.endDate = cursor.getString(cursor.getColumnIndex(Employee.EndDate1));
//                person.status = cursor.getString(cursor.getColumnIndex(Employee.Status));
//                person.comment = cursor.getString(cursor.getColumnIndex(Employee.Comment));
//
//            }while(cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return taskList;
//    }


}
