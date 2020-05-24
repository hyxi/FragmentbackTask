package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.PictureBean;

import java.util.List;

/**
 * Created by user on 2016/1/27.
 */
public class PictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<PictureBean> picList;
    private LayoutInflater layoutInflater;

    public PictureAdapter(Context mContext, List<PictureBean> picList) {
        this.mContext = mContext;
        this.picList = picList;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_img_adapter, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.tv_picName = (TextView) convertView.findViewById(R.id.tv_picName);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.imageView.setImageResource(picList.get(position).getImage());
        holder.tv_picName.setText(picList.get(position).getPicName());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView tv_picName;
    }

}
