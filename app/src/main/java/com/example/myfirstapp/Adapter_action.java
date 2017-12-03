package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

/**
 * Created by qq on 2017/12/3.
 */

public class Adapter_action extends BaseAdapter{
    List<Map<String, Object>> mlistems;
    Context mContext;

    public Adapter_action(Context context, List<Map<String, Object>> listems) {
        mlistems = listems;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mlistems.size();
    }

    @Override
    public Object getItem(int i) {
        return mlistems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    ViewHolder mHolder;

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        mHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_list_cell, null);
            mHolder.tv1 = (TextView) view.findViewById(R.id.project_id);
            mHolder.tv2 = (TextView) view.findViewById(R.id.project_name);
            mHolder.tv3 = (TextView) view.findViewById(R.id.project_owner);
            mHolder.btn = (Button) view.findViewById(R.id.project_status);
            mHolder.et1 = (EditText) view.findViewById(R.id.project_comment);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
        mlistems.get(position).get("ID");
        //如果没有这些内容，将会显示布局文件中的内容
        mHolder.tv1.setText(mlistems.get(position).get("ID").toString());
        mHolder.tv2.setText(mlistems.get(position).get("TaskName").toString());
        mHolder.tv3.setText(mlistems.get(position).get("Owner1").toString());
        mHolder.btn.setText(mlistems.get(position).get("Status").toString());
        //mHolder.et1.setText(mlistems.get(position).get("Comment").toString());
        //头像的点击事件并传值
        mHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Status");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setSingleChoiceItems(new String[] { "ON", "OFF" }, 0,null);
                builder.setPositiveButton("Confirm", null);
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        Button btn;
        EditText et1;
    }


}
