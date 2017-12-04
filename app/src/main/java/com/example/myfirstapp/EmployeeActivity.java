package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
                Employee.Mobile + "," + Employee.Phone
                +") VALUES ('" +
                person.name + "','"+ person.title +"','"+ person.office +"','"+
                person.department +"', '"+ person.team +"', '" + person.email +"', '"+
                person.mobile+"', '"+ person.phone +"')";

        //Inserting Row
        db.execSQL(insertQuery);
        db.close(); //Closing database connection
    }

    public void delete(int employee_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Employee.TABLE, Employee.KEY_ID + "= ?", new String[] { String.valueOf(employee_Id) });
        db.close(); // Closing database connection
    }


    public void update(Employee employee){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.Employee_Name, employee.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {employee.name});
        db.close(); // Closing database connection

    }

    public void updateEmployee(Employee employee){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.Employee_Name, employee.name);
        values.put(Employee.Title, employee.title);
        values.put(Employee.Email, employee.email);
        values.put(Employee.Phone, employee.phone);
        values.put(Employee.Department, employee.department);
        values.put(Employee.Team, employee.team);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.KEY_ID + "= ?", new String[] {String.valueOf(employee.empolyee_ID)});
        db.close(); // Closing database connection
    }


    public void updateRow(Employee employee){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.X, employee.row);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {employee.name});
        db.close(); // Closing database connection

    }

    public void updateLocation(Employee employee){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Employee.X, employee.row);
        values.put(Employee.Y, employee.column);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Employee.TABLE, values, Employee.Employee_Name + "= ?", new String[] {employee.name});
        db.close(); // Closing database connection
    }

//    public ArrayList<HashMap<String,String>> getEmployeeList(){
//        //Open connection to read only
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT " +
//                Employee.KEY_ID + "," +
//                Employee.Employee_Name+
//                " FROM " + Employee.TABLE;
//
//        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                HashMap<String, String> student = new HashMap<String, String>();
//                student.put("ID", cursor.getString(cursor.getColumnIndex(Employee.KEY_ID)));
//                student.put("EmployeeName", cursor.getString(cursor.getColumnIndex(Employee.Employee_Name)));
//                studentList.add(student);
//
//            }while(cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return studentList;
//    }

    public Employee getEmployeeById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Employee.TABLE +
                " WHERE " + Employee.KEY_ID + "=?";
        Employee newemployee = new Employee();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if(cursor.moveToFirst()){
            do{
                newemployee.empolyee_ID = cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
                newemployee.name = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
                newemployee.phone = cursor.getString(cursor.getColumnIndex(Employee.Phone));
                newemployee.title = cursor.getString(cursor.getColumnIndex(Employee.Title));
                newemployee.email = cursor.getString(cursor.getColumnIndex(Employee.Email));
                newemployee.department = cursor.getString(cursor.getColumnIndex(Employee.Department));
                newemployee.team = cursor.getString(cursor.getColumnIndex(Employee.Team));




            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return newemployee;

    }

    //Find the row of previous employee
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

    //Find the row of next employee
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

        ArrayList<String> employeeList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                String employee;
                employee = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
                employeeList.add(employee);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return employeeList;
    }

    public ArrayList<Employee> getEmployeesByDepartmentAndTeam(String dep, String team){
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Employee.TABLE + " WHERE Department = ? AND Team = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dep, team});
        ArrayList<Employee> employee_List = new ArrayList<Employee>();

        if(cursor.moveToFirst()){
            do{
                Employee newemployee = new Employee();
                newemployee.empolyee_ID = cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
                newemployee.name = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
                newemployee.phone = cursor.getString(cursor.getColumnIndex(Employee.Phone));
                newemployee.title = cursor.getString(cursor.getColumnIndex(Employee.Title));
                newemployee.email = cursor.getString(cursor.getColumnIndex(Employee.Email));
                employee_List.add(newemployee);

            }while(cursor.moveToNext());
        }
        return employee_List;
    }

//    public ArrayList<Employee> getEmployeesByOwnerName(String name){
//        SQLiteDatabase db =dbHelper.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + Employee.TABLE + " WHERE EmployeeName = ?";
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});
//        ArrayList<Employee> employee_List = new ArrayList<Employee>();
//
//        if(cursor.moveToFirst()){
//            do{
//                Employee newemployee = new Employee();
//                newemployee.employee_ID = cursor.getInt(cursor.getColumnIndex(Employee.KEY_ID));
//                newemployee.name = cursor.getString(cursor.getColumnIndex(Employee.Employee_Name));
//                newemployee.prevEmployee = cursor.getString(cursor.getColumnIndex(Employee.Prev_Employee));
//                newemployee.nextEmployee = cursor.getString(cursor.getColumnIndex(Employee.Next_Employee));
//                newemployee.mainEmployee = cursor.getString(cursor.getColumnIndex(Employee.Main_Employee));
//                newemployee.row = cursor.getInt(cursor.getColumnIndex(Employee.X));
//                newemployee.column = cursor.getInt(cursor.getColumnIndex(Employee.Y));
//                newemployee.rank = cursor.getInt(cursor.getColumnIndex(Employee.Rank));
//                newemployee.level = cursor.getInt(cursor.getColumnIndex(Employee.Level));
//                newemployee.owner = cursor.getString(cursor.getColumnIndex(Employee.Owner1));
//                newemployee.status = cursor.getString(cursor.getColumnIndex(Employee.Status));
//                newemployee.startDate = cursor.getString(cursor.getColumnIndex(Employee.StartDate1));
//                newemployee.endDate = cursor.getString(cursor.getColumnIndex(Employee.EndDate1));
//                newemployee.comment = cursor.getString(cursor.getColumnIndex(Employee.Comment));
//                employee_List.add(newemployee);
//
//            }while(cursor.moveToNext());
//        }
//        return employee_List;
//    }


}
