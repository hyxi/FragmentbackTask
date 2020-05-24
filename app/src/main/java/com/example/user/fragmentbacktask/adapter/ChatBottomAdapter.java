package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

import java.util.ArrayList;

/**
 * Created by user on 2016/11/21.
 */

public class ChatBottomAdapter extends BaseAdapter {

    private int[] pics;
    private Context mContext;
    private ArrayList<String> list;
    private LayoutInflater inflater;

    public ChatBottomAdapter(Context context,ArrayList<String> list,int[] icons) {
        this.mContext = context;
        this.list = list;
        this.pics = icons;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.chat_bottom_function_list_item, null);
            holder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_picture.setImageResource(pics[position]);
        holder.tv_name.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder{
        TextView tv_name;
        ImageView iv_picture;
    }

}
