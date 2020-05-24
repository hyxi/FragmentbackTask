package com.example.user.fragmentbacktask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.fragmentbacktask.view.DottedLineView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huangyuxi on 2019-08-05
 * Title: 纠错状态
 */
public class StateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StateBean> states = new ArrayList<>();
    private Context mContext;

    public StateAdapter(Context context) {
        mContext = context;
    }

    public void addList(List<StateBean> list) {
        this.states.clear();
        this.states.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.state_view_holder, parent, false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StateViewHolder stateHolder = (StateViewHolder) holder;
        if (position == states.size() -1) {
            stateHolder.bottomDotView.setVisibility(View.GONE);
        } else {
            stateHolder.bottomDotView.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            stateHolder.topDotView.setVisibility(View.INVISIBLE);
        } else {
            stateHolder.topDotView.setVisibility(View.VISIBLE);
        }
        StateBean stateBean = states.get(position);
        stateHolder.tvTime.setText(stateBean.getTime());
        stateHolder.tvState.setText(stateBean.getMessage());

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    class StateViewHolder extends RecyclerView.ViewHolder {

        private DottedLineView topDotView;
        private DottedLineView bottomDotView;
        private TextView tvTime;
        private TextView tvState;

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            topDotView = itemView.findViewById(R.id.dot_top_line);
            bottomDotView = itemView.findViewById(R.id.dot_bottom_line);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvState = itemView.findViewById(R.id.tv_state);
        }
    }

}
