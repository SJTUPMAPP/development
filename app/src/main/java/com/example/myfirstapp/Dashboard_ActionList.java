package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dashboard_ActionList extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<Task> tasklist;
    Task task = new Task();

    public Dashboard_ActionList() {
    }
    public  static Dashboard_ActionList newInstance(int sectionNumber) {
        Dashboard_ActionList fragment = new Dashboard_ActionList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_dashboard_actionlist , container, false);
        TaskActivity TaskAct = new TaskActivity(getContext());
        tasklist = TaskAct.getActionList();
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tasklist.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("ID", Integer.toString(tasklist.get(i).task_ID));
            listem.put("TaskName", tasklist.get(i).name);
            listem.put("Owner1", tasklist.get(i).owner);
            listem.put("Status", tasklist.get(i).status);
            listem.put("Comment", tasklist.get(i).comment);
            listems.add(listem);
        }

        ListView lv=(ListView)rootView.findViewById(R.id.data_lv);
        ListAdapter adapter = new SimpleAdapter(getActivity(),listems, R.layout.fragment_list_cell, new String[] { "ID","TaskName","Owner1", "Status", "Comment"},
                new int[] {R.id.project_id, R.id.project_name,R.id.project_owner, R.id.project_status, R.id.project_comment});
        lv.setAdapter(adapter);

    return rootView;}
}
