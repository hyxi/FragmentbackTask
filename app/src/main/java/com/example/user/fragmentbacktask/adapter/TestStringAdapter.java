package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

import java.util.ArrayList;

/**
 * Created by hyxi
 *
 * @date 2017--03--26
 */
public class TestStringAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> arrayList;
    private LayoutInflater mInflater;


    public TestStringAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        mInflater = LayoutInflater.from(mContext);
    }

    public ArrayList<String> getData(){
        return arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public void addDataToBottom(ArrayList<String> newData) {
        arrayList.addAll(newData);
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_test, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_test = (TextView) convertView.findViewById(R.id.tv_test);
        String content  =arrayList.get(position);
        holder.tv_test.setText(content);

        return convertView;
    }

    private class ViewHolder{
       private TextView tv_test;
    }


}
