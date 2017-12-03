package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

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
                +"," + Task.Next_Task + "," + Task.Main_Task + "," + Task.Level +  "," +Task.Status + "," + Task.Percentage + ","+ Task.Comment+ ") VALUES ('"
                + newtask.name + "','"+ newtask.owner + "', datetime('now', 'localtime'), '"+ newtask.startDate +"','"+ newtask.endDate +"','"+ newtask.prevTask +"', '"
                + newtask.nextTask +"', '"+ newtask.mainTask+"',"+ newtask.level +",'" + newtask.status +"'," + newtask.percentage +",'" + newtask.comment +"')";

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

    public void updateTask(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Task_Name, task.name);
        values.put(Task.Owner1, task.owner);
        values.put(Task.StartDate1, task.startDate);
        values.put(Task.EndDate1, task.endDate);
        values.put(Task.Status, task.status);
        values.put(Task.Percentage, task.percentage);
        values.put(Task.Comment, task.comment);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.KEY_ID + "= ?", new String[] {String.valueOf(task.task_ID)});
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

    public void updateRoot(Task task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Main_Task, task.mainTask);

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


    public Task getTaskById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE +
                " WHERE " + Task.KEY_ID + "=?";
        Task newtask = new Task();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});


        if(cursor.moveToFirst()){
            do{

                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
            }while(cursor.moveToNext());
        }
        return newtask;
    }

    public void updatePrevTask(String prevTask, String task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Next_Task, task + "-");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {prevTask});
        db.close(); // Closing database connection

    }
    public void updatePrevTaskExisted(String prevTask, String task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Task newtask = getTaskByName(prevTask);
        Task newtask1 = getTaskByName(task);

        ArrayList<String> nextTaskList = new ArrayList<>();
        String nextListString = newtask.nextTask;
        String tempListString = "";

        ContentValues values = new ContentValues();

        if (!Objects.equals(nextListString,new String("NONE"))){
            if (nextListString.indexOf("-") == -1){
                nextTaskList.add(nextListString);
            }
            else {
                int pos = 0;
                while (nextListString.indexOf("-",pos) != -1){
                    int nextPos = nextListString.indexOf("-",pos);
                    nextTaskList.add(nextListString.substring(pos,nextPos));
                    pos = nextPos + 1;
                }
            }
            for(int j = 0; j < nextTaskList.size(); j++) {
                if(!Objects.equals(nextTaskList.get(j),newtask1.nextTask))
                    tempListString = tempListString + nextTaskList.get(j) +"-";
            }

                values.put(Task.Next_Task, tempListString + task +  "-");
        }

        else values.put(Task.Next_Task, task + "-");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {prevTask});
        db.close(); // Closing database connection

    }

    public void updateNextTask(String nextTask, String task){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.Prev_Task, task);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {nextTask});
        db.close(); // Closing database connection

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

    //Find the column of previous task
    public int findPrevColumn(String prevTask){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int column = 0;
        String selectQuery = "SELECT " +  Task.Y + " FROM " + Task.TABLE + " WHERE TaskName = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{prevTask});
        if(cursor.moveToFirst()){
            do{
                column = cursor.getInt(cursor.getColumnIndex(Task.Y));
            }while(cursor.moveToNext());
        }
        return column;
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

    public int findMaxColumn(int level, int row){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int maxColumn = 0;
        int tempColumn;
        String selectQuery = "SELECT " + Task.Y + " FROM " + Task.TABLE + " WHERE Level = ? AND X = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level), String.valueOf(row)});
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

    public int findMaxColumn(int level){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int maxColumn = 0;
        int tempColumn;
        String selectQuery = "SELECT MAX(" + Task.Y + ") FROM " + Task.TABLE + " WHERE Level = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level)});
        if(cursor.moveToFirst()){
            do{
                maxColumn = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return maxColumn;
    }

    //Increase the value of the row, when its row >= row
    public void LowerRow(int level, int row, String root){
        Task newtask = new Task();
        int id;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Task.KEY_ID + "," + Task.X + " FROM " + Task.TABLE + " WHERE Level = ? AND MainTask = ? AND X >= ? ";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level), root, String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask = getTaskById(id);
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.row = newtask.row + 1;
                updateRow(newtask);

            }while(cursor.moveToNext());
        }


    }

    public void LowerRow(String task){
        Task nowtask = getTaskByName(task);
        if(!Objects.equals(nowtask.nextTask,new String("NONE"))) {
            Task nexttask = getTaskByName(nowtask.nextTask);
            ArrayList<String> nextTaskList = new ArrayList<>();
            String nextListString = nexttask.nextTask;

            if (!Objects.equals(nextListString, new String("NONE"))) {
                if (nextListString.indexOf("-") == -1) {
                    nextTaskList.add(nextListString);
                } else {
                    int pos = 0;
                    while (nextListString.indexOf("-", pos) != -1) {
                        int nextPos = nextListString.indexOf("-", pos);
                        nextTaskList.add(nextListString.substring(pos, nextPos));
                        pos = nextPos + 1;
                    }
                }
                for (int j = 0; j < nextTaskList.size(); j++) {
                    Task subNextTask = getTaskByName(nextTaskList.get(j));
                    subNextTask.row = subNextTask.row + 1;
                    updateRow(subNextTask);
                    LowerRow(subNextTask.name);
                }
            }
        }

    }

    public void insertroot(int level, int row, String root, String newroot){
        Task newtask = new Task();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE + " WHERE Level = ? AND MainTask = ? AND X >= ? ";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(level), root, String.valueOf(row)});
        if(cursor.moveToFirst()){
            do{
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask =  getTaskById(newtask.task_ID);
                newtask.row = newtask.row + 1;
                newtask.mainTask = newroot;
                updateRow(newtask);
                updateRoot(newtask);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        newtask = getTaskByName(newroot);
        ContentValues values = new ContentValues();
        values.put(Task.Main_Task, newtask.name);
        // It's a good practice to use parameter ?, instead of concatenate string
        db1.update(Task.TABLE, values, Task.Task_Name + "= ?", new String[] {newtask.name});
        db1.close();
    }

    public ArrayList<String> getTaskNameList(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Task.KEY_ID + "," +
                Task.Task_Name+
                " FROM " + Task.TABLE;

        ArrayList<String> taskList = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                String task;
                task = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                taskList.add(task);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<Task> getActionList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " +Task.TABLE;
        ArrayList<Task> taskList = new ArrayList<Task>();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
                taskList.add(newtask);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public ArrayList<Task> getButtonList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Task.KEY_ID + "," +
                Task.Task_Name +"," +
                Task.X + "," +
                Task.Y +
                " FROM " +Task.TABLE;
        ArrayList<Task> taskList = new ArrayList<Task>();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                taskList.add(newtask);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public Task getTaskByName(String name){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE + " WHERE TaskName LIKE '%" + name + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Task newtask = new Task();
        if(cursor.moveToFirst()){
            do{
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
            }while(cursor.moveToNext());
        }
        return newtask;
    }

    public ArrayList<Task> getRootTasks(){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE + " WHERE PrevTask = 'NONE'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Task> task_List = new ArrayList<Task>();

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
                task_List.add(newtask);

            }while(cursor.moveToNext());
        }
        return task_List;
    }

   public ArrayList<Task> getTasksByOwnerName(String name){
       SQLiteDatabase db =dbHelper.getReadableDatabase();
       String selectQuery = "SELECT * FROM " + Task.TABLE + " WHERE Owner1 = ?";
       Cursor cursor = db.rawQuery(selectQuery, new String[]{name});
       ArrayList<Task> task_List = new ArrayList<Task>();

       if(cursor.moveToFirst()){
           do{
               Task newtask = new Task();
               newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
               newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
               newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
               newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
               newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
               newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
               newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
               newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
               newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
               newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
               newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
               newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
               newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
               newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
               newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
               task_List.add(newtask);

           }while(cursor.moveToNext());
       }
       return task_List;
   }

    public Task getTaskByLocation(int x, int y){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE + " WHERE X = ? AND Y = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(x),String.valueOf(y)});
        Task newtask = new Task();
        if(cursor.moveToFirst()){
            do{
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
            }while(cursor.moveToNext());
        }
        return newtask;
    }

    public ArrayList<Task> getTasksByWeek(){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE +
                " WHERE DATE(EndDate1) >= DATE('now', 'localtime','-1 day') " +
                "AND DATE(EndDate1) < DATE('now','localtime','+7 day') " +
                "ORDER BY DATE(EndDate1) ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Task> task_List = new ArrayList<Task>();

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
                task_List.add(newtask);

            }while(cursor.moveToNext());
        }
        return task_List;
    }
    public int getTasksByWeekCount(){
        int cnt = -1;
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE +
                " WHERE DATE(EndDate1) >= DATE('now', 'localtime','-1 day') " +
                "AND DATE(EndDate1) < DATE('now','localtime','+7 day') ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                 cnt = cursor.getCount();
            }while(cursor.moveToNext());
        }
        return cnt;
    }
    public ArrayList<Task> getTasksByDate(String date){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE +
                " WHERE DATE(EndDate1) = DATE(" + date + ")";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Task> task_List = new ArrayList<Task>();

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
                task_List.add(newtask);

            }while(cursor.moveToNext());
        }
        return task_List;
    }


    public ArrayList<Task> getTasksByMonth(int month){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Task.TABLE +
                " WHERE STRFTIME('%m', EndDate1) = " + month ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Task> task_List = new ArrayList<Task>();

        if(cursor.moveToFirst()){
            do{
                Task newtask = new Task();
                newtask.task_ID = cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                newtask.name = cursor.getString(cursor.getColumnIndex(Task.Task_Name));
                newtask.prevTask = cursor.getString(cursor.getColumnIndex(Task.Prev_Task));
                newtask.nextTask = cursor.getString(cursor.getColumnIndex(Task.Next_Task));
                newtask.mainTask = cursor.getString(cursor.getColumnIndex(Task.Main_Task));
                newtask.row = cursor.getInt(cursor.getColumnIndex(Task.X));
                newtask.column = cursor.getInt(cursor.getColumnIndex(Task.Y));
                newtask.rank = cursor.getInt(cursor.getColumnIndex(Task.Rank));
                newtask.level = cursor.getInt(cursor.getColumnIndex(Task.Level));
                newtask.owner = cursor.getString(cursor.getColumnIndex(Task.Owner1));
                newtask.status = cursor.getString(cursor.getColumnIndex(Task.Status));
                newtask.startDate = cursor.getString(cursor.getColumnIndex(Task.StartDate1));
                newtask.endDate = cursor.getString(cursor.getColumnIndex(Task.EndDate1));
                newtask.percentage = cursor.getInt(cursor.getColumnIndex(Task.Percentage));
                newtask.comment = cursor.getString(cursor.getColumnIndex(Task.Comment));
                task_List.add(newtask);

            }while(cursor.moveToNext());
        }
        return task_List;
    }
}
