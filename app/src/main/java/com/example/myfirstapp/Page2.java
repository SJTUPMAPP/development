package com.example.myfirstapp;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Page2 extends Fragment {
    public static Page2 newInstance(String info) {
        Bundle args = new Bundle();
        Page2 fragment = new Page2();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page2, null);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        tvInfo.setText(getArguments().getString("info"));
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;

    }
}