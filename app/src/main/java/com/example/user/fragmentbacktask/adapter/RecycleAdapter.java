package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.recyclecutomview.OnItemRecListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * recycleAdapter
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> dataList = new ArrayList<>();
    private OnItemRecListener itemRecListener;

    public void setOnItemRecClickListener(OnItemRecListener clickListener){
         this.itemRecListener = clickListener;
    }

    public RecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public RecycleAdapter(Context mContext, ArrayList<String> list) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        addList(list);
    }


    public void addList(ArrayList<String> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecycleAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("adapter","onCreateViewHolder");
        //第三个参数，是否绑定到根root
        View view  = mInflater.inflate(R.layout.item_recy_text,parent,false);
        RecViewHolder holder = new RecViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecycleAdapter.RecViewHolder holder, final int position) {
        Log.i("adapter","bind"+position+"");
        holder.tv_test.setText(dataList.get(position));
        holder.itemView.setTag(dataList.get(position));
        if (itemRecListener != null) {
            holder.tv_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemRecListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_test;

        public RecViewHolder(View itemView) {
            super(itemView);
            tv_test = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }

}
