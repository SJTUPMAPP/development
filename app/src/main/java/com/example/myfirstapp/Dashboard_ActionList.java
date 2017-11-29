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


/**
 * Created by qq on 2017/11/4.
 */

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

        //ListView lv = (ListView)rootView.findViewById(R.id.data_lv);
        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
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
        int k = 0;
//        lv.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return tasklist.size();
//            }
//            //View view = View.inflate(getContext(),R.layout.fragment_list_cell,null);
//            //ListView的每一个条目都是一个view对象
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                task = tasklist.get(position);
//                View view;
//                //从studentlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
//                if(convertView==null){
//                    view = View.inflate(getContext(),R.layout.fragment_list_cell,null);
//                }
//                else{
//                    view = convertView;
//                }
//                //view = View.inflate(getContext(),R.layout.fragment_list_cell,null);
//                TextView project_id = (TextView) view.findViewById(R.id.project_id);
//                TextView project_name = (TextView) view.findViewById(R.id.project_name);
//                TextView project_owner = (TextView) view.findViewById(R.id.project_owner);
//                Button project_status = (Button) view.findViewById(R.id.project_status);
//                Button project_comment = (Button) view.findViewById(R.id.project_comment);
//                project_id.setText(Integer.toString(task.task_ID));
//                project_name.setText(task.name);
//                project_owner.setText(task.owner);
//                project_status.setText(task.status);
//                project_comment.setText(task.comment);
////                project_id.setText(Integer.toString(1));
////                project_name.setText("a");
////                project_owner.setText("a");
////                project_status.setText("a");
////                project_comment.setText("a");
//                return view;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//        });
    return rootView;}
}
