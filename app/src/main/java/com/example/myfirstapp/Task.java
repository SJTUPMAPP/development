package com.example.myfirstapp;

/**
 * Created by User on 2017/10/4.
 */

public class Task {
    // Labels table name
    public static final String TABLE = "Student";

    // Labels Table Columns names
    public static final String KEY_ID = "ID";
    public static final String Task_Name = "TaskName";
    public static final String Prev_Task = "PrevTask";
    public static final String Next_Task = "NextTask";
    public static final String Main_Task = "MainTask";
    public static final String Rank = "Rank";
    public static final String Level = "Level";
    public static final String X = "X";
    public static final String Y = "Y";
    public static final String Owner1 = "Owner1";
    public static final String Owner2 = "Owner2";
    public static final String Owner3 = "Owner3";
    public static final String Status = "Status";
    public static final String CreateTime = "CreateTime";
    public static final String StartDate1 = "StartDate1";
    public static final String EndDate1 = "EndDate1";
    public static final String AlterTime1 = "AlterTime1";
    public static final String StartDate2 = "StartDate2";
    public static final String EndDate2 = "EndDate2";
    public static final String AlterTime2 = "AlterTime2";
    public static final String StartDate3 = "StartDate3";
    public static final String EndDate3 = "EndDate3";
    public static final String Comment = "Comment";




    // property help us to keep data
    public int task_ID;
    public String name;
    public String owner;
    public String mainTask
    public String prevTask;
    public String nextTask;
    public String startDate;
    public String endDate;
    public int row;
    public int column;


}