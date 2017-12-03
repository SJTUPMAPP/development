package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.R.id.parent;


public class Dashboard_ActionList extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<Task> tasklist;
    Task task = new Task();
    Adapter_action adapter;
    public Dashboard_ActionList() {
    }
    public  static Dashboard_ActionList newInstance(int sectionNumber) {
        Dashboard_ActionList fragment = new Dashboard_ActionList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView= inflater.inflate(R.layout.fragment_dashboard_actionlist , container, false);
        View rootView = inflater.inflate(R.layout.fragment_dashboard_actionlist, null);
        ListView lv=(ListView)rootView.findViewById(R.id.data_lv);

        TaskActivity TaskAct = new TaskActivity(getContext());
        tasklist = TaskAct.getActionList();
        final List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tasklist.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("ID", Integer.toString(tasklist.get(i).task_ID));
            listem.put("TaskName", tasklist.get(i).name);
            listem.put("Owner1", tasklist.get(i).owner);
            listem.put("Status", tasklist.get(i).status);
            listem.put("Comment", tasklist.get(i).comment);
            listems.add(listem);
        }
        adapter = new Adapter_action(getActivity(), listems);
        lv.setAdapter(adapter);
/*        View view = inflater.inflate(R.layout.fragment_list_cell, null);

        Button button = (Button) view.findViewById(R.id.project_status);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 显示对话框
                Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("单选框");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setSingleChoiceItems(new String[] { "Item1", "Item2" }, 0,null);
                builder.setPositiveButton("确定", null);
                builder.setNegativeButton("取消", null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });*/
        //ListAdapter adapter = new SimpleAdapter(getActivity(),listems, R.layout.fragment_list_cell, new String[] { "ID","TaskName","Owner1", "Status", "Comment"},
                //new int[] {R.id.project_id, R.id.project_name,R.id.project_owner, R.id.project_status, R.id.project_comment});
    return rootView;}

}

