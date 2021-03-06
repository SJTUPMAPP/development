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
        updateAllButtons(view);

        return view;
    }

    public void showAddTaskDialog() {
        DialogFragment newFragment = new AddTaskDialogFragment();
        newFragment.show(this.getChildFragmentManager(), "dialog_fragment");
    }


    @Override
    public void onDialogPositiveClick(final String name, int row, int column) {
        updateAllButtons(getView());
    }

    @Override
    public void onDialogNegativeClick(AddTaskDialogFragment dialog) {
        // User touched the dialog's negative button
    }

    public void updateAllButtons(View view) {
        TaskActivity Tact = new TaskActivity(getActivity());
        existingTasks = Tact.getButtonList();
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int gap = width/10;
        String name; int row, column, mId;
        ConstraintLayout conLayout = view.findViewById(R.id.fragment_project_layout);
        conLayout.removeAllViews();
        int maxHeight = 0, maxWidth = 0;


        for (int i = 0; i < existingTasks.size(); i++){
            final Task task = existingTasks.get(i);
            name = task.name;
            row = task.row;
            column = task.column;
            Button  mButton = new Button(getContext());
            mButton.setWidth(width/3);
            mButton.setHeight(height/12);
            //mButton.setAutoSizeTextTypeWithDefaults(mButton.AUTO_SIZE_TEXT_TYPE_UNIFORM);
            mButton.setAutoSizeTextTypeUniformWithConfiguration(6, 15, 1, 1);
            mButton.setText(name);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    DialogFragment newFragment = AddTaskDialogFragment.newInstance(task);
                    ViewEditTaskDialogFragment newFragment = ViewEditTaskDialogFragment.newInstance(task);
                    newFragment.show(getChildFragmentManager(),"dialog_fragment");
                }
            });
            mId = conLayout.generateViewId();
            mButton.setId(mId);

            conLayout.addView(mButton);
            ConstraintSet set = new ConstraintSet();
            set.clone(conLayout);
            row = row - 1;
            column = column -1;
            int h = height/6*row+gap*row;
            set.connect(mId, TOP, R.id.fragment_project_layout, TOP, h );
            if (h > maxHeight) {
                maxHeight = h;
            }
            int w = width/4*column+gap*column;
            set.connect(mId, ConstraintSet.START, R.id.fragment_project_layout, ConstraintSet.START, w);
            if (w > maxWidth) {
                maxWidth = w;
            }
            set.applyTo(conLayout);
        }

        final DrawView dv = new DrawView(getContext(),width,height);
        dv.invalidate();
        dv.setMinimumHeight(maxHeight + 100);
        dv.setMinimumWidth(maxWidth + 500);
        conLayout.addView(dv);
    }

}