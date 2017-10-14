package com.example.myfirstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.myfirstapp.R.id.project_name;

/**
 * Created by elvischen on 04/10/2017.
 */
public class AddTaskDialogFragment extends android.support.v4.app.DialogFragment {
    addTaskDialogListener mListener;
//    EditText project_name = findView
    Button btnDatePicker;
    private int mYear, mMonth, mDay;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText project_name = getDialog().findViewById(R.id.project_name);
                String name = project_name.getText().toString();
                mListener.onDialogPositiveClick(name);
//                sendResult(0);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddTaskDialogFragment.this);
            }
        });

        btnDatePicker=(Button) view.findViewById(R.id.btn_date);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return builder.create();
    }

    public void showDatePicker() {
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

                            btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
    }

    public interface addTaskDialogListener {
//        void onDialogPositiveClick(AddTaskDialogFragment dialog);
        void onDialogPositiveClick(String name);
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
