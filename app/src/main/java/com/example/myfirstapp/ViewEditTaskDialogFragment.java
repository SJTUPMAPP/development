package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by elvischen on 03/12/2017.
 */

public class ViewEditTaskDialogFragment extends android.support.v4.app.DialogFragment {

    int editFlag = 0;
    Button startButton,endButton;

    public ViewEditTaskDialogFragment(){

    }

    public static ViewEditTaskDialogFragment newInstance(Task task){
        ViewEditTaskDialogFragment fragment = new ViewEditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt("TaskID",task.task_ID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_viewedit_task, null);
        builder.setView(view);
        TaskActivity Tact = new TaskActivity(getContext());
        Task task = Tact.getTaskById(getArguments().getInt("TaskID"));
        android.support.v7.widget.AppCompatImageButton mEdit = view.findViewById(R.id.edit_task);
        final TextView nameView = view.findViewById(R.id.project_name_view);
        nameView.setText(task.name);
        final EditText nameEdit = view.findViewById(R.id.project_name_edit);
        final TextView ownerView = view.findViewById(R.id.project_owner_view);
        ownerView.setText(task.owner);
        final Spinner ownerEdit = view.findViewById(R.id.project_owner_edit);
        EmployeeActivity Eact = new EmployeeActivity(getContext());
        ArrayList owner_data_list = Eact.getEmployeeNameList();
        owner_data_list.add(0,"NONE");
        ArrayAdapter<String> owner_arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, owner_data_list);
        owner_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerEdit.setAdapter(owner_arr_adapter);


        final TextView statusView = view.findViewById(R.id.project_status_view);
        statusView.setText(task.status);
        final Spinner statusEdit = view.findViewById(R.id.project_status_edit);
        final TextView percentageView = view.findViewById(R.id.project_percentage_view);
        percentageView.setText(Integer.toString(task.percentage));
        final EditText percentageEdit = view.findViewById(R.id.project_percentage_edit);
        final TextView commentView = view.findViewById(R.id.project_comment_view);
        commentView.setText(task.comment);
        final EditText commentEdit = view.findViewById(R.id.project_comment_edit);

        final TextView startView = view.findViewById(R.id.project_startdate_view);
        final TextView endView = view.findViewById(R.id.project_enddate_view);
        startView.setText(task.startDate.toString());
        endView.setText(task.endDate.toString());
        startButton = view.findViewById(R.id.btn_start_date);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
        endButton = view.findViewById(R.id.btn_end_date);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        builder.setPositiveButton(new String("Update"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(new String("Cancel"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFlag == 0){
                    nameView.setVisibility(View.GONE);
                    nameEdit.setVisibility(View.VISIBLE);
                    nameEdit.setText(nameView.getText().toString());
                    ownerView.setVisibility(View.GONE);
                    ownerEdit.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    statusEdit.setVisibility(View.VISIBLE);
                    startView.setVisibility(View.GONE);
                    startButton.setVisibility(View.VISIBLE);
                    startButton.setText(startView.getText().toString());
                    endView.setVisibility(View.GONE);
                    endButton.setVisibility(View.VISIBLE);
                    endButton.setText(endView.getText().toString());
                    percentageView.setVisibility(View.GONE);
                    percentageEdit.setVisibility(View.VISIBLE);
                    percentageEdit.setText(percentageView.getText().toString());
                    commentView.setVisibility(View.GONE);
                    commentEdit.setVisibility(View.VISIBLE);
                    commentEdit.setText(commentView.getText().toString());
//                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
//                    ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
                    editFlag = 1;
                }
                else {
                    nameView.setVisibility(View.VISIBLE);
                    nameEdit.setVisibility(View.GONE);
                    nameView.setText(nameEdit.getText().toString());
                    ownerView.setVisibility(View.VISIBLE);
                    ownerEdit.setVisibility(View.GONE);
                    ownerView.setText(ownerEdit.getSelectedItem().toString());
                    statusView.setVisibility(View.GONE);
                    statusEdit.setVisibility(View.GONE);
                    startView.setVisibility(View.VISIBLE);
                    startButton.setVisibility(View.GONE);
                    startView.setText(startButton.getText().toString());
                    endView.setVisibility(View.VISIBLE);
                    endButton.setVisibility(View.GONE);
                    endView.setText(endButton.getText().toString());
                    statusView.setText(statusEdit.getSelectedItem().toString());
                    percentageView.setVisibility(View.VISIBLE);
                    percentageEdit.setVisibility(View.GONE);
                    percentageView.setText(percentageEdit.getText().toString());
                    commentView.setVisibility(View.VISIBLE);
                    commentEdit.setVisibility(View.GONE);
                    commentView.setText(commentEdit.getText().toString());
                    editFlag = 0;
                }

            }
        });
        AlertDialog dialog = builder.create();
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        return dialog;
    }
    private int mYear, mMonth, mDay;

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
                                startButton.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                break;
                            case R.id.btn_end_date:
                                endButton.setText(year+ "-" + String.format("%02d",(monthOfYear + 1)) + "-" + String.format("%02d",dayOfMonth));
                                break;
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
