package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


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
        ListView lv = (ListView)rootView.findViewById(R.id.data_lv);
        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
        TaskActivity TaskAct = new TaskActivity(getContext());
        tasklist = TaskAct.getActionList();
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return tasklist.size();
            }

            //ListView的每一个条目都是一个view对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                task = tasklist.get(0);
                View view;
                //从studentlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                if(convertView==null){
                    view = View.inflate(getContext(),R.layout.fragment_list_cell,null);
                }
                else{
                    view = convertView;
                }
                TextView project_id = (TextView) view.findViewById(R.id.project_id);
                TextView project_name = (TextView) view.findViewById(R.id.project_name);
                TextView project_owner = (TextView) view.findViewById(R.id.project_owner);
                Button project_status = (Button) view.findViewById(R.id.project_status);
                Button project_comment = (Button) view.findViewById(R.id.project_comment);
                project_id.setText(Integer.toString(task.task_ID));
                project_name.setText(task.name);
                project_owner.setText(task.owner);
                project_status.setText(task.status);
                project_comment.setText(task.comment);
                return view;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

        });
    return rootView;}
}
