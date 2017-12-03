package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elvischen on 14/10/2017.
 */

public class ViewPersonDialogFragment extends android.support.v4.app.DialogFragment {

    public static ViewPersonDialogFragment newInstance(int id) {
        ViewPersonDialogFragment fragment = new ViewPersonDialogFragment();
        Bundle args = new Bundle();
        args.putInt("EmpID",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_person, null);
        builder.setView(view);
        TaskActivity Tact = new TaskActivity(getContext());
        EmployeeActivity Eact = new EmployeeActivity(getContext());
        int employeeID = getArguments().getInt("EmpID");
        Employee employee = Eact.getEmployeeById(employeeID);
        String employeeName = employee.name;
        TextView nameView = view.findViewById(R.id.person_name);
        nameView.setText(employeeName);
        TextView titleView = view.findViewById(R.id.person_title);
        titleView.setText(employee.title);
        TextView emailView = view.findViewById(R.id.person_email);
        emailView.setText(employee.email);
        TextView numberView = view.findViewById(R.id.person_number);
        numberView.setText(employee.mobile);
        TextView departmentView = view.findViewById(R.id.view_department);
        departmentView.setText(employee.department);
        TextView teamView = view.findViewById(R.id.view_team);
        teamView.setText(employee.team);
        ArrayList<Task> tasklist = Tact.getTasksByOwnerName(employeeName);
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tasklist.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("TaskName", tasklist.get(i).name);
            listem.put("Owner1", tasklist.get(i).owner);
            listem.put("Status", tasklist.get(i).status);
            listems.add(listem);
        }
        ListView lv=(ListView)view.findViewById(R.id.data_lv);
        ListAdapter adapter = new SimpleAdapter(getActivity(),listems, R.layout.view_person_listcell, new String[] { "TaskName","Owner1", "Status"},
                new int[] { R.id.project_name,R.id.project_owner, R.id.project_status});
        lv.setAdapter(adapter);


//        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
////                Employee employee = new Employee();
////                EmployeeActivity EmpAct = new EmployeeActivity(getContext());
////                EditText person_name = getDialog().findViewById(R.id.person_name);
////                employee.name = person_name.getText().toString();
////                EditText title = getDialog().findViewById(R.id.person_title);
////                employee.title = title.getText().toString();
////                EditText email = getDialog().findViewById(R.id.person_email);
////                employee.email = email.getText().toString();
////                EditText phone_number = getDialog().findViewById(R.id.person_number);
////                employee.phone = phone_number.getText().toString();
////                spinner_department = getDialog().findViewById(R.id.spinner_department);
////                employee.department = spinner_department.getSelectedItem().toString();
////                spinner_team = getDialog().findViewById(R.id.spinner_team);
////                employee.team = spinner_team.getSelectedItem().toString();
////                EmpAct.insert(employee);
////
////                mListener.onDialogPositiveClick(employee.name);
//            }
//        });
//        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
////                mListener.onDialogNegativeClick(AddPersonDialogFragment.this);
//            }
//        });

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
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (AddPersonDialogFragment.addPersonDialogListener) getParentFragment();
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(context.toString()
//                    + " must implement addPersonDialogListener");
//        }
//    }
}
