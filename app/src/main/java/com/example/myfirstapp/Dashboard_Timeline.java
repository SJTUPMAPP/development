package com.example.myfirstapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link Dashboard_Timeline.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard_Timeline#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard_Timeline extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView Rv;
    private ArrayList<HashMap<String,Object>> listItem;
    private MyAdapter myAdapter;

    public Dashboard_Timeline() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Dashboard_Timeline newInstance(int sectionNumber) {
        Dashboard_Timeline fragment = new Dashboard_Timeline();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard_timeline, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        initData();
        // 绑定数据到RecyclerView
        Rv = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        //使用线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Rv.setLayoutManager(layoutManager);
        Rv.setHasFixedSize(true);

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

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        HashMap<String, Object> map6 = new HashMap<String, Object>();

        map1.put("ItemTitle", "美国谷歌公司已发出");
        map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
        listItem.add(map1);

        map2.put("ItemTitle", "国际顺丰已收入");
        map2.put("ItemText", "等待中转");
        listItem.add(map2);

        map3.put("ItemTitle", "国际顺丰转件中");
        map3.put("ItemText", "下一站中国");
        listItem.add(map3);

        map4.put("ItemTitle", "中国顺丰已收入");
        map4.put("ItemText", "下一站广州华南理工大学");
        listItem.add(map4);

        map5.put("ItemTitle", "中国顺丰派件中");
        map5.put("ItemText", "等待派件");
        listItem.add(map5);

        map6.put("ItemTitle", "华南理工大学已签收");
        map6.put("ItemText", "收件人:Carson");
        listItem.add(map6);
    }


}


