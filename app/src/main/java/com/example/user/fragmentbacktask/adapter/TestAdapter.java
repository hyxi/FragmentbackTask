package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.TestBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hyxi
 *
 * @date 2017--04--24
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TestBean> list;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addDatas(List<TestBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    int count = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        count++;
        return new TestAdapter.ViewHolder(mContext, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        TestAdapter.ViewHolder viewholder = (TestAdapter.ViewHolder) holder;
        TestBean data = list.get(position);
        viewholder.textView.setText(data.getContent());
        if(position != 0 && data.getId() != 0){
            data.setId(position);
        }
        if(position == 0){
            viewholder.line1.setVisibility(View.INVISIBLE);
        }else{
            viewholder.line1.setVisibility(View.VISIBLE);
        }

        if(position != list.size()-1){
            viewholder.line2.setVisibility(View.VISIBLE);
//            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            viewholder.textView.measure(w, h);
//            int height = viewholder.textView.getMeasuredHeight();
//            int th = viewholder.textView.getMeasuredHeight();
//            if(th>StringUtils.dp2px(80f)){
//                height = th;
//            }
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewholder.line2.getLayoutParams();
//            params.height = height - StringUtils.dp2px(12f);
//            viewholder.line2.setLayoutParams(params);
        }else{
            viewholder.line2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private View line1;
        private View line2;
        private LinearLayout ll_content;

        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.recycler_test_layout, parent, false));
            textView = (TextView) itemView.findViewById(R.id.textview);
            line1 = itemView.findViewById(R.id.line1);
            line2 = itemView.findViewById(R.id.line2);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_content);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
