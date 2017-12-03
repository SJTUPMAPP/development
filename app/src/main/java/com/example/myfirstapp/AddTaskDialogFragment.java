package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by elvischen on 04/10/2017.
 */
public class AddTaskDialogFragment extends android.support.v4.app.DialogFragment {
    addTaskDialogListener mListener;
//    EditText project_name = findView

    private int mYear, mMonth, mDay;

    private Spinner spinner_owner;
    private List<String> owner_data_list;
    private Spinner spinner_prevtask;
    private List<String> prevtask_data_list;
    private Spinner spinner_nexttask;
    private List<String> nexttask_data_list;
    private ArrayAdapter<String> owner_arr_adapter;
    private ArrayAdapter<String> prevtask_arr_adapter;
    private ArrayAdapter<String> nexttask_arr_adapter;
    private Spinner status;

    private Button startDatePicker, endDatePicker, startDatePicker2, endDatePicker2;
    private EditText comment,project_name,percentage;

    public AddTaskDialogFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);
        TaskActivity Tact = new TaskActivity(getContext());

        project_name = view.findViewById(R.id.project_name);
        spinner_owner = view.findViewById(R.id.spinner_owner);
        comment = view.findViewById(R.id.project_comment);
        spinner_prevtask = view.findViewById(R.id.spinner_prevtask);
        spinner_nexttask = view.findViewById(R.id.spinner_nexttask);
        percentage = view.findViewById(R.id.project_percentage);
        status = view.findViewById(R.id.spinner_status);

        //        Date picker settings
        startDatePicker = view.findViewById(R.id.btn_start_date);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        endDatePicker = view.findViewById(R.id.btn_end_date);
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        startDatePicker2 = view.findViewById(R.id.btn_start_date2);
        endDatePicker2 = view.findViewById(R.id.btn_end_date2);
        startDatePicker2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        endDatePicker2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });


        prevtask_data_list = new ArrayList<String>();
        prevtask_data_list = Tact.getTaskNameList();
        prevtask_data_list.add(0,"NONE");
        nexttask_data_list = new ArrayList<String>();
        nexttask_data_list = Tact.getTaskNameList();
        nexttask_data_list.add(0,"NONE");

        EmployeeActivity Eact = new EmployeeActivity(getContext());
        owner_data_list = Eact.getEmployeeNameList();
        owner_data_list.add(0,"NONE");


        //适配器
        owner_arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, owner_data_list);
        prevtask_arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, prevtask_data_list);
        nexttask_arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nexttask_data_list);
        //设置样式
        owner_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prevtask_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nexttask_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_owner.setAdapter(owner_arr_adapter);
        spinner_prevtask.setAdapter(prevtask_arr_adapter);
        spinner_nexttask.setAdapter(nexttask_arr_adapter);


        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Task task = new Task();
                TaskActivity TaskAct = new TaskActivity(getContext());
                task.name = project_name.getText().toString();
                task.prevTask = spinner_prevtask.getSelectedItem().toString();
                task.nextTask = spinner_nexttask.getSelectedItem().toString();
                task.owner = spinner_owner.getSelectedItem().toString();
                task.startDate = startDatePicker.getText().toString();
                task.endDate = endDatePicker.getText().toString();
                if(task.prevTask == "NONE" && task.nextTask == "NONE")
                task.startDate2 = startDatePicker2.getText().toString();
                task.endDate2 = endDatePicker2.getText().toString();
                task.comment = comment.getText().toString();
                task.status = status.getSelectedItem().toString();
                if (Objects.equals(percentage.getText().toString(),new String(""))){
                    task.percentage = 0;
                }
                else {
                    task.percentage = Integer.parseInt(percentage.getText().toString());
                }

                if(task.prevTask == "NONE")
                    task.mainTask = task.name;
                else if(task.prevTask == "NONE" && task.nextTask != "NONE")
                    task.mainTask = TaskAct.getTaskByName(task.nextTask).mainTask;

                else task.mainTask = TaskAct.getTaskByName(task.prevTask).mainTask;
                task.level = 0;
                TaskAct.insert(task);

                if(task.nextTask != "NONE"){
                    TaskAct.updateNextTask(task.nextTask,task.name);
                    // TODO: 02/12/2017 Update PrevTask of NextTask 
                }

                if(task.prevTask == "NONE" && task.nextTask == "NONE" ) {
                    task.row = 1;
                    //task.column = TaskAct.findMaxColumn(task.level,task.row)+1;
                    task.column = TaskAct.findMaxColumn(task.level)+1;
                }

                else if(task.prevTask == "NONE" && task.nextTask != "NONE" ) {
                    task.row = 1;
                    task.column = TaskAct.findPrevColumn(task.nextTask);
                    TaskAct.insertroot(task.level, TaskAct.findNextRow(task.nextTask), task.mainTask, task.name);
                }
                else {
                    task.row = TaskAct.findPrevRow(task.prevTask) + 1;


                    //insert as the last node
                    if (task.nextTask == "NONE") {
                        TaskAct.updatePrevTaskExisted(task.prevTask, task.name);
                        int maxColumn = TaskAct.findMaxColumn(task.level, task.row) + 1;
                        if (maxColumn < TaskAct.findPrevColumn(task.prevTask))
                            maxColumn = TaskAct.findPrevColumn(task.prevTask);
                        task.column = maxColumn;
                    }

                    //insert into two adjacent nodes
                    else if (TaskAct.findNextRow(task.nextTask) - TaskAct.findPrevRow(task.prevTask) == 1) {
                        task.column = TaskAct.findPrevColumn(task.nextTask);
                        TaskAct.updatePrevTaskExisted(task.prevTask, task.name);
                        Task n = TaskAct.getTaskByName(task.nextTask);
                        n.row = n.row + 1;
                        TaskAct.updateRow(n);
                        TaskAct.LowerRow(task.name);

                    }

                    //insert into two existed nodes, but not adjacent
                    else if (TaskAct.findNextRow(task.nextTask) - TaskAct.findPrevRow(task.prevTask) > 1) {
                        task.column = TaskAct.findMaxColumn(task.level, task.row) + 1;
                        TaskAct.updatePrevTaskExisted(task.prevTask, task.name);

                    }

                    //insert for connect two nodes with different root
                    else {
                        // TODO: 02/12/2017 insert for connect two nodes with different root
                        TaskAct.updatePrevTaskExisted(task.prevTask, task.name);
                        task.column = TaskAct.findMaxColumn(task.level, task.row) + 1;
                    }
                }

                TaskAct.updateLocation(task);
                mListener.onDialogPositiveClick(task.name, task.row, task.column);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddTaskDialogFragment.this);
            }
        });


        return builder.create();

    }

    public void showDatePicker(final View v) {
            // Get Current Date
            // works in a minimum api of 24
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            switch (v.getId()){
                                case R.id.btn_start_date:
                                    startDatePicker.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    startDatePicker2.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    break;
                                case R.id.btn_end_date:
                                    endDatePicker.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    endDatePicker2.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    break;
                                case R.id.btn_start_date2:
                                    startDatePicker2.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    break;
                                case R.id.btn_end_date2:
                                    endDatePicker2.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                    break;
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
    }

    public interface addTaskDialogListener {
        void onDialogPositiveClick(String name, int row, int column);
        void onDialogNegativeClick(AddTaskDialogFragment dialog);

    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (addTaskDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement addTaskDialogListener");
        }
    }
}
