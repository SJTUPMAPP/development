package com.example.myfirstapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import static android.support.constraint.ConstraintLayout.LayoutParams.TOP;


public class Project extends Fragment implements AddTaskDialogFragment.addTaskDialogListener{
    public static Project newInstance(String info) {
        Bundle args = new Bundle();
        Project fragment = new Project();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Task> existingTasks;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, null);
        FloatingActionButton addBtn;
        addBtn = view.findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });
        TaskActivity Tact = new TaskActivity(getActivity());
        existingTasks = Tact.getButtonList();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int gap = width/10;
        String name; int row, column, mId;

        for (int i = 0; i < existingTasks.size(); i++){
            ConstraintLayout conLayout = view.findViewById(R.id.fragment_project_layout);
            name = existingTasks.get(i).name;
            row = existingTasks.get(i).row;
            column = existingTasks.get(i).column;
            Button  mButton = new Button(getContext());
            mButton.setText(name);
//            mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DialogFragment newFragment = new AddTaskDialogFragment();
//                    newFragment.show(getChildFragmentManager(),"dialog_fragment");
//                }
//            });
            mId = conLayout.generateViewId();
            mButton.setId(mId);
            conLayout.addView(mButton);
            ConstraintSet set = new ConstraintSet();
            set.clone(conLayout);
            set.connect(mId, TOP, R.id.fragment_project_layout, TOP, height/6*row+gap*row );
            set.connect(mId, ConstraintSet.START, R.id.fragment_project_layout, ConstraintSet.START, width/4*column+gap*column);
            set.applyTo(conLayout);
        }

//        final DrawView drawview = new DrawView(getContext(),100,100,100,200);
//        view.setMinimumHeight(500);
//        view.setMinimumWidth(300);
//        view.invalidate();
//        conLayout.addView(drawview);
        return view;
    }

    public void showAddTaskDialog() {
        DialogFragment newFragment = new AddTaskDialogFragment();
        newFragment.show(this.getChildFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onDialogPositiveClick(final String name, int row, int column) {
        // User touched the dialog's positive button
        //RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.fragment_relative_layout);
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_project_layout);
        Button  mButton = new Button(getContext());
        mButton.setText(name);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AddTaskDialogFragment();
                newFragment.show(getChildFragmentManager(),"dialog_fragment");
            }
        });
        int mId = constraintLayout.generateViewId();
        mButton.setId(mId);
        constraintLayout.addView(mButton);

        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int gap = width/10;
        set.connect(mId, TOP, R.id.fragment_project_layout, TOP, height/6*row+gap*row );
        set.connect(mId, ConstraintSet.START, R.id.fragment_project_layout, ConstraintSet.START, width/4*column+gap*column);
        set.applyTo(constraintLayout);

        // TODO: 04/11/2017 get task lists and related row and column from the database
        updateAllViews();

        Log.e("",row+"row");
        Log.e("", column+"column");
        Log.e("", mId+"mId");
    }

    @Override
    public void onDialogNegativeClick(AddTaskDialogFragment dialog) {
        // User touched the dialog's negative button

    }

    public void updateAllViews() {

    }

}