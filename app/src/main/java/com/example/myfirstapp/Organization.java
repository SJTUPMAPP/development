package com.example.myfirstapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.support.constraint.ConstraintLayout.LayoutParams.BOTTOM;


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
        View view = inflater.inflate(R.layout.fragment_organization, null);
        addBtn = view.findViewById(R.id.btn_add_person);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAddPersonDialog();
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
        ConstraintLayout constraintLayout = (ConstraintLayout) getView().findViewById(R.id.fragment_organization_layout);
        Button mButton = new Button(getContext());
        mButton.setText(name);
        int mId = constraintLayout.generateViewId();
        mButton.setId(mId);
        constraintLayout.addView(mButton);

        int width = constraintLayout.getWidth();
        int height = constraintLayout.getHeight();
        ConstraintSet set = new ConstraintSet();
        // You may want (optional) to start with the existing constraint,
        // so uncomment this.
        set.clone(constraintLayout);
        // Resize to 100dp
        // center horizontally in the container
        set.centerHorizontally(mId, R.id.fragment_organization_layout);
        // pin to the bottom of the container
        set.connect(mId, BOTTOM, R.id.fragment_organization_layout, BOTTOM, height/2);
        // Apply the changes
        set.applyTo(constraintLayout);
    }

    @Override
    public void onDialogNegativeClick(AddPersonDialogFragment dialog) {
        // User touched the dialog's negative button

    }


}
