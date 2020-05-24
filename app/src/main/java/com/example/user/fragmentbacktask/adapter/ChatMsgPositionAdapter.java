package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.ChatPositionInfo;
import com.example.user.fragmentbacktask.utils.StringUtils;

import java.util.ArrayList;

/**
 * IM聊天位置选择Adapter
 */
public class ChatMsgPositionAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<ChatPositionInfo> data = new ArrayList<>();

    public ChatMsgPositionAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.chat_position_list_item,null);
            holder = new ViewHolder();
            holder.tv_place = (TextView) convertView.findViewById(R.id.tv_place);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatPositionInfo posInfo = data.get(position);
        String place = !StringUtils.isNullOrEmpty(posInfo.place)?posInfo.place:"";
        holder.tv_place.setText(place);
        String address = !StringUtils.isNullOrEmpty(posInfo.address)?posInfo.address:"";
        holder.tv_address.setText(address);
        if(posInfo.isChoose){
            holder.iv_choose.setVisibility(View.VISIBLE);
        }else{
            holder.iv_choose.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void resetData(ArrayList<ChatPositionInfo> list){
        data.clear();
        data.addAll(list);
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tv_place;
        private TextView tv_address;
        private ImageView iv_choose;
    }
}
