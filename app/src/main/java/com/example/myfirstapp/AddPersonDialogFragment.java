package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvischen on 14/10/2017.
 */

public class AddPersonDialogFragment extends android.support.v4.app.DialogFragment {
    AddPersonDialogFragment.addPersonDialogListener mListener;

    private Spinner spinner_department;
    private List<String> department_data_list;
    private ArrayAdapter<String> department_arr_adapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_person, null);
        builder.setView(view);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText project_name = getDialog().findViewById(R.id.person_name);
                String name = project_name.getText().toString();
                mListener.onDialogPositiveClick(name);
//                sendResult(0);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddPersonDialogFragment.this);
            }
        });

        spinner_department = (Spinner) view.findViewById(R.id.spinner_department);

        department_data_list = new ArrayList<String>();
        department_data_list.add("Market");
        department_data_list.add("Tech");
        department_data_list.add("Design");
        department_data_list.add("International");


        department_arr_adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, department_data_list);
        department_arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(department_arr_adapter);

        return builder.create();

    }



    public interface addPersonDialogListener {
        void onDialogPositiveClick(String name);
        void onDialogNegativeClick(AddPersonDialogFragment dialog);
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddPersonDialogFragment.addPersonDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement addPersonDialogListener");
        }
    }
}
