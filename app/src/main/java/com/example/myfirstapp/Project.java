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

import static android.support.constraint.ConstraintLayout.LayoutParams.TOP;


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
        ConstraintLayout conLayout = view.findViewById(R.id.fragment_project_layout);
        final DrawView drawview = new DrawView(getContext(),100,100,100,200);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        view.invalidate();
        conLayout.addView(drawview);
        return view;
    }

    public void showAddTaskDialog() {
        DialogFragment newFragment = new AddTaskDialogFragment();
        newFragment.show(this.getChildFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onDialogPositiveClick(String name, int row, int column) {
        // User touched the dialog's positive button
        //RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.fragment_relative_layout);
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_project_layout);
        Button  mButton = new Button(getContext());
        mButton.setText(name);
        int mId = constraintLayout.generateViewId();
        mButton.setId(mId);
        constraintLayout.addView(mButton);

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int gap = width/10;
        //int width = constraintLayout.getWidth();
        //int height = constraintLayout.getHeight();
        ConstraintSet set = new ConstraintSet();
        // You may want (optional) to start with the existing constraint,
        // so uncomment this.
        set.clone(constraintLayout);
        // Resize to 100dp
        // center horizontally in the container
        set.connect(mId, TOP, R.id.fragment_project_layout, TOP, height/6*row+gap*row );
        // pin to the bottom of the container
        set.connect(mId, ConstraintSet.START, R.id.fragment_project_layout, ConstraintSet.START, width/4*column+gap*column);
        // Apply the changes
        set.applyTo(constraintLayout);
        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mButton.getLayoutParams();
        //params.topMargin = height/6*row;
        //params.leftMargin = width/4*column;
        //mButton.setLayoutParams(params);
        Log.e("",row+"row");
        Log.e("", column+"column");
        Log.e("", mId+"mId");
    }

    @Override
    public void onDialogNegativeClick(AddTaskDialogFragment dialog) {
        // User touched the dialog's negative button

    }

}