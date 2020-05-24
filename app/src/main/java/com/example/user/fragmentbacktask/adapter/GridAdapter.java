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
 * Created by user on 2016/5/27.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> dataList;

    public GridAdapter(Context mContext, ArrayList<String> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_recy_text, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content = (TextView) convertView.findViewById(R.id.tv_test);
        holder.tv_content.setText(dataList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
    }
}
