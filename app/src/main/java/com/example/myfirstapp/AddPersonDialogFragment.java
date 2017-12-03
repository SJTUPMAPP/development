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

import java.util.List;

/**
 * Created by elvischen on 14/10/2017.
 */

public class AddPersonDialogFragment extends android.support.v4.app.DialogFragment {
    AddPersonDialogFragment.addPersonDialogListener mListener;

    private Spinner spinner_department;

    private Spinner spinner_team;

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
                Employee employee = new Employee();
                EmployeeActivity EmpAct = new EmployeeActivity(getContext());
                EditText person_name = getDialog().findViewById(R.id.person_name);
                employee.name = person_name.getText().toString();
                EditText title = getDialog().findViewById(R.id.person_title);
                employee.title = title.getText().toString();
                EditText email = getDialog().findViewById(R.id.person_email);
                employee.email = email.getText().toString();
                EditText phone_number = getDialog().findViewById(R.id.person_number);
                employee.phone = phone_number.getText().toString();
                spinner_department = getDialog().findViewById(R.id.spinner_department);
                employee.department = spinner_department.getSelectedItem().toString();
                spinner_team = getDialog().findViewById(R.id.spinner_team);
                employee.team = spinner_team.getSelectedItem().toString();
                EmpAct.insert(employee);

                mListener.onDialogPositiveClick(employee.name);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogNegativeClick(AddPersonDialogFragment.this);
            }
        });

        return builder.create();
    }

    public void setArrayAdapter(List array, Spinner sp) {
        ArrayAdapter<String> arr_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, array);;
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(arr_adapter);
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
