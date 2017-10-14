package com.example.myfirstapp;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.constraint.ConstraintLayout.LayoutParams.BOTTOM;


public class Project extends Fragment implements AddTaskDialogFragment.addTaskDialogListener{
    public static Project newInstance(String info) {
        Bundle args = new Bundle();
        Project fragment = new Project();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    private FloatingActionButton addBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, null);
        addBtn = view.findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        return view;
    }

    public void showAddTaskDialog() {
        DialogFragment newFragment = new AddTaskDialogFragment();
//        newFragment.setTargetFragment(this,0);
        newFragment.show(this.getChildFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onDialogPositiveClick(String name) {
        // User touched the dialog's positive button
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_project_layout);
        Button  mButton = new Button(getContext());
        mButton.setText(name);
        int mId = constraintLayout.generateViewId();
        mButton.setId(mId);
        constraintLayout.addView(mButton);

        int width = constraintLayout.getWidth();
        int height = constraintLayout.getHeight();
//        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        ConstraintSet set = new ConstraintSet();
        // You may want (optional) to start with the existing constraint,
        // so uncomment this.
        set.clone(constraintLayout);
        // Resize to 100dp
        // center horizontally in the container
        set.centerHorizontally(mId, R.id.fragment_project_layout);
        // pin to the bottom of the container
        set.connect(mId, BOTTOM, R.id.fragment_project_layout, BOTTOM, height/2);
        // Apply the changes
        set.applyTo(constraintLayout);
    }

    @Override
    public void onDialogNegativeClick(AddTaskDialogFragment dialog) {
        // User touched the dialog's negative button

    }

}