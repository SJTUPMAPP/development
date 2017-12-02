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

/**
 * Created by elvischen on 04/10/2017.
 */
public class AddTaskDialogFragment extends android.support.v4.app.DialogFragment {
    addTaskDialogListener mListener;
//    EditText project_name = findView
    Button startDatePicker;
    Button endDatePicker;

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


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        TaskActivity Tact = new TaskActivity(getContext());

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


        spinner_owner = view.findViewById(R.id.spinner_owner);
        spinner_prevtask = view.findViewById(R.id.spinner_prevtask);
        spinner_nexttask = view.findViewById(R.id.spinner_nexttask);

        //数据
        // TODO: 29/11/2017 set default choice to "None" 
        prevtask_data_list = new ArrayList<String>();
        prevtask_data_list = Tact.getTaskNameList();
        prevtask_data_list.add(0,"NONE");
        nexttask_data_list = new ArrayList<String>();
        nexttask_data_list = Tact.getTaskNameList();
        nexttask_data_list.add(0,"NONE");

        owner_data_list = new ArrayList<String>();
        owner_data_list.add("Elvis");
        owner_data_list.add("Timmy");
        owner_data_list.add("Marx");
        owner_data_list.add("Jason");

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
                EditText project_name = getDialog().findViewById(R.id.project_name);
                task.name = project_name.getText().toString();
                spinner_prevtask = getDialog().findViewById(R.id.spinner_prevtask);
                task.prevTask = (String)spinner_prevtask.getSelectedItem();
                spinner_nexttask = getDialog().findViewById(R.id.spinner_nexttask);
                task.nextTask = (String)spinner_nexttask.getSelectedItem();
                spinner_owner = getDialog().findViewById(R.id.spinner_owner);
                task.owner = (String)spinner_owner.getSelectedItem();
                task.startDate = startDatePicker.getText().toString();
                task.endDate = endDatePicker.getText().toString();
                if(task.prevTask == "NONE")
                    task.mainTask = task.name;
                else task.mainTask = TaskAct.getTaskByName(task.prevTask).mainTask;
                task.level = 0;
                TaskAct.insert(task);

                if(task.nextTask != "NONE"){
                    TaskAct.updateNextTask(task.nextTask,task.name);
                }

                if(task.prevTask == "NONE") {
                    task.row = 1;
                    //task.column = TaskAct.findMaxColumn(task.level,task.row)+1;
                    task.column = TaskAct.findMaxColumn(task.level)+1;
                }

                else {
                    task.row = TaskAct.findPrevRow(task.prevTask) + 1;


                    //insert as the last node
                    if (task.nextTask == "NONE") {
                        TaskAct.updatePrevTask(task.prevTask, task.name);
                        int maxColumn = TaskAct.findMaxColumn(task.level, task.row) + 1;
                        if (maxColumn < TaskAct.findPrevColumn(task.prevTask))
                            maxColumn = TaskAct.findPrevColumn(task.prevTask);
                        task.column = maxColumn;
                    }

                    //insert into two adjacent nodes
                    else if (TaskAct.findNextRow(task.nextTask) - TaskAct.findPrevRow(task.prevTask) == 1) {
                        TaskAct.updatePrevTask(task.prevTask, task.name);
                        TaskAct.LowerRow(task.level, TaskAct.findNextRow(task.nextTask), task.mainTask);
                        task.column = TaskAct.findPrevColumn(task.prevTask);
                    }

                    //insert into two existed nodes, but not adjacent
                    else if (TaskAct.findNextRow(task.nextTask) - TaskAct.findPrevRow(task.prevTask) > 1) {
                        TaskAct.updatePrevTaskExisted(task.prevTask, task.name);
                        task.column = TaskAct.findMaxColumn(task.level, task.row) + 1;
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

                            if (v.getId() == R.id.btn_start_date) {
                                startDatePicker.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                            }
                            else {
                                endDatePicker.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
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
