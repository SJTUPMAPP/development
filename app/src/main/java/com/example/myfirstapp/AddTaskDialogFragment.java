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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.myfirstapp.R.id.project_name;

/**
 * Created by elvischen on 04/10/2017.
 */
public class AddTaskDialogFragment extends android.support.v4.app.DialogFragment {
    addTaskDialogListener mListener;
//    EditText project_name = findView
    Button startDatePicker;
    Button endDatePicker;

    private int mYear, mMonth, mDay;
    public class Order{
        public int row, column;
        public String prevtask, nexttask;
        public Order(String prevtask, String nexttask){
            this.prevtask = prevtask;
            this.nexttask = nexttask;
        }
    }
    private Spinner spinner_owner;
    private List<String> owner_data_list;
    private Spinner spinner_prevtask;
    private List<String> prevtask_data_list;
    private Spinner spinner_nexttask;
    private List<String> nexttask_data_list;
    private ArrayAdapter<String> owner_arr_adapter;
    private ArrayAdapter<String> prevtask_arr_adapter;
    private ArrayAdapter<String> nexttask_arr_adapter;
    public  int amount = 0;


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
                Order []order = new Order[1000];
                EditText project_name = getDialog().findViewById(R.id.project_name);
                String name = project_name.getText().toString();
                spinner_prevtask = getDialog().findViewById(R.id.spinner_prevtask);
                //order.prevtask = (String)spinner_prevtask.getSelectedItem();
                spinner_nexttask = getDialog().findViewById(R.id.spinner_nexttask);
                //order.nexttask = (String)spinner_nexttask.getSelectedItem();
                order[amount] = new Order((String)spinner_prevtask.getSelectedItem(),(String)spinner_nexttask.getSelectedItem());
                amount++;

                mListener.onDialogPositiveClick(name, order[amount].row, order[amount].column);
//                sendResult(0);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddTaskDialogFragment.this);
            }
        });

//        Date picker settings
        startDatePicker=(Button) view.findViewById(R.id.btn_start_date);
        startDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        endDatePicker=(Button) view.findViewById(R.id.btn_end_date);
        endDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

//        owner spinner settings
        spinner_owner = (Spinner) view.findViewById(R.id.spinner_owner);
        spinner_prevtask = (Spinner) view.findViewById(R.id.spinner_prevtask);
        spinner_nexttask = (Spinner) view.findViewById(R.id.spinner_nexttask);

        //数据
        owner_data_list = new ArrayList<String>();
        owner_data_list.add("Elvis");
        owner_data_list.add("Timmy");
        owner_data_list.add("Marx");
        owner_data_list.add("Jason");

        prevtask_data_list = new ArrayList<String>();
        prevtask_data_list.add("Shopping");
        prevtask_data_list.add("Studying");
        prevtask_data_list.add("Movies");
        prevtask_data_list.add("Sleeping");
        prevtask_data_list.add("NULL");

        nexttask_data_list = new ArrayList<String>();
        nexttask_data_list.add("Shopping");
        nexttask_data_list.add("Studying");
        nexttask_data_list.add("Movies");
        nexttask_data_list.add("Sleeping");
        prevtask_data_list.add("NULL");

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
                                startDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                            else {
                                endDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
