package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard_Timeline extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView Rv;
    private ArrayList<HashMap<String,Object>> listItem;
    private MyAdapter myAdapter;

    public Dashboard_Timeline() {

    }

    public static Dashboard_Timeline newInstance(int sectionNumber) {
        Dashboard_Timeline fragment = new Dashboard_Timeline();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup main_content,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard_timeline, main_content, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        initData();
        // 绑定数据到RecyclerView
        Rv = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        //使用线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv.setLayoutManager(layoutManager);
        //用自定义分割线类设置分割线
        Rv.addItemDecoration(new DividerItemDecoration(getActivity()));
        //为ListView绑定适配器
        myAdapter = new MyAdapter(getActivity(),listItem);
        Rv.setAdapter(myAdapter);
        return rootView;
    }


    //        init data for the timeline
    public void initData(){
        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
        ArrayList<Task> tasklist = new ArrayList<>();
        TaskActivity TaskAct = new TaskActivity(getContext());
        tasklist = TaskAct.getTasksByWeek();
        int cnt = TaskAct.getTasksByWeekCount();
        for (int i = 0; i < cnt; i++){
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTitle", tasklist.get(i).name);
            map.put("ItemText", tasklist.get(i).owner);
            listItem.add(map);
        }
    }


}


