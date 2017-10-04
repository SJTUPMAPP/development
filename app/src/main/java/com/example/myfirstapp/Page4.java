package com.example.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class Page4 extends Fragment {
    public static Page4 newInstance(String info) {
        Bundle args = new Bundle();
        Page4 fragment = new Page4();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine, container, false);
        //TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        TextView myData = view.findViewById(R.id.my_data);
        TextView myFile = view.findViewById(R.id.my_file);
        TextView logOut = view.findViewById(R.id.log_out);
        //tvInfo.setText(getArguments().getString("info"));
        /*tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT).show();
            }
        });
        */
        return view;

    }
}
