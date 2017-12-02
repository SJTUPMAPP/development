package com.example.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;


public class Organization extends Fragment implements AddPersonDialogFragment.addPersonDialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Organization() {
        // Required empty public constructor
    }

    public static Organization newInstance(String param1) {
        Organization fragment = new Organization();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    private FloatingActionButton addBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_organization, null);
        addBtn = view.findViewById(R.id.btn_add_person);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddPersonDialog();
            }
        });
        Button findPersonButton = view.findViewById(R.id.btn_find_person);
        findPersonButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EmployeeActivity EAct = new EmployeeActivity(getActivity());
                List listOfEmp;
                Spinner depSpinner = view.findViewById(R.id.org_spinner_departments);
                String departments = depSpinner.getSelectedItem().toString();
                Spinner teamSpinner = view.findViewById(R.id.org_spinner_teams);
                String teams = teamSpinner.getSelectedItem().toString();
                listOfEmp =  EAct.getEmployeesByDepartmentAndTeam(departments,teams);
                //// TODO: 02/12/2017 Render people onto the page 
            }
        });
        return view;
    }

    public void showAddPersonDialog() {
        DialogFragment newFragment = new AddPersonDialogFragment();
        newFragment.show(this.getChildFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onDialogPositiveClick(String name) {
        // User touched the dialog's positive button

    }

    @Override
    public void onDialogNegativeClick(AddPersonDialogFragment dialog) {
        // User touched the dialog's negative button

    }


}
