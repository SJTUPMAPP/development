package com.example.myfirstapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import static android.support.constraint.ConstraintLayout.LayoutParams.TOP;


public class Organization extends Fragment implements AddPersonDialogFragment.addPersonDialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Employee> existingEmployees;

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
                ArrayList<Employee> listOfEmp;
                Spinner depSpinner = view.findViewById(R.id.org_spinner_departments);
                String departments = depSpinner.getSelectedItem().toString();
                Spinner teamSpinner = view.findViewById(R.id.org_spinner_teams);
                String teams = teamSpinner.getSelectedItem().toString();
                listOfEmp =  EAct.getEmployeesByDepartmentAndTeam(departments,teams);
                //// TODO: 02/12/2017 Render people onto the page
                updateAllButtons(view, listOfEmp);
            }
        });
        return view;
    }

    public void updateAllButtons(View view, ArrayList<Employee> employeeList) {
        TaskActivity Tact = new TaskActivity(getActivity());
        existingEmployees = employeeList;
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int gap = width/2000;
        String name, title, phone;
        int row, column, mId;
        ConstraintLayout conLayout = view.findViewById(R.id.employee_layout);
        conLayout.removeAllViews();
//        final DrawView dv = new DrawView(getContext(),10,10,100,100);
//        dv.setMinimumWidth(500);
//        dv.setMinimumHeight(500);
//        dv.invalidate();
//        conLayout.addView(dv);

        for (int i = 0; i < employeeList.size(); i++){
            name = existingEmployees.get(i).name;
            //title = existingEmployees.get(i).title;
            // phone = existingEmployees.get(i).phone;

            title = String.valueOf(width);
            phone = String.valueOf(height);
            column = i%2+1;
            row = i/2+1;

            Button  mButton = new Button(getContext());
            mButton.setWidth(7*width/20);
            mButton.setHeight(height/8);
            mButton.setText(name+"\n"+title+"\n"+phone);
            //mButton.setAutoSizeTextTypeWithDefaults(mButton.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            mButton.setAutoSizeTextTypeUniformWithConfiguration(6, 15, 1, 1);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new AddTaskDialogFragment();
                    newFragment.show(getChildFragmentManager(),"dialog_fragment");
                }
            });
            mId = conLayout.generateViewId();
            mButton.setId(mId);
            conLayout.addView(mButton);
            ConstraintSet set = new ConstraintSet();
            set.clone(conLayout);
            set.connect(mId, TOP, R.id.employee_layout, TOP, height/8*(row-1)+gap*row);
            set.connect(mId, ConstraintSet.START, R.id.employee_layout, ConstraintSet.START, width/2*(column-1));
            set.applyTo(conLayout);
        }
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
