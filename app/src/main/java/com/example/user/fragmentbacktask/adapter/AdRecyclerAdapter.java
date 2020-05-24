package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burke
 * 带广告的历史view
 */
public class AdRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    public static final int PULLUP_LOAD_MORE = 3;
    public static final int LOADING_MORE = 4;
    public static final int LOADING_FAIL = 5;
    public static final int LOADING_SUCCESS = 6;
    public static final int NO_MORE_DATA = 7;

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> datalist = new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;
    private int load_more_status;
    private OnItemClickListener mListener;

    public AdRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void addMoreItem(List<String> newDatas) {
        datalist.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = datalist.size();
        if (mHeaderView != null) {
            count++;
        }
        if (mFooterView != null) {
            count++;
        }
        return count;
    }


    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position + 1 == getItemCount()) {
                if(load_more_status == NO_MORE_DATA){
                    return TYPE_NORMAL;
                }else{
                    return TYPE_FOOTER;
                }
            } else {
                return TYPE_NORMAL;
            }
        } else {
            if (position + 1 == getItemCount()) {
                if(load_more_status == NO_MORE_DATA){
                    return TYPE_NORMAL;
                }else{
                    return TYPE_FOOTER;
                }
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    public void addDatas(ArrayList<String> list) {
        datalist.clear();
        datalist.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        if (viewType == TYPE_FOOTER) {
            mFooterView = mInflater.inflate(R.layout.recycler_view_footer_layout, parent, false);
            return new FootViewHolder(mFooterView);
        }
        View view = null;
        return new AdRecyclerAdapter.NormalHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        if (holder instanceof FootViewHolder) {
            AdRecyclerAdapter.FootViewHolder footViewHolder = (FootViewHolder) holder;
            String footContent = "";
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footContent = "上拉加载更多...";
                    break;
                case LOADING_MORE:
                    footContent = "正在加载更多数据...";
                    break;
                case LOADING_SUCCESS:
                    footContent = "数据加载成功";
                    break;
                case LOADING_FAIL:
                    footContent = "数据加载失败";
                    break;
            }
            footViewHolder.tv_item_foot.setText(footContent);
            return;
        }
        AdRecyclerAdapter.NormalHolder normalHolder = (NormalHolder) holder;
        Log.i("tag", "position:" + position);
        if (mListener != null) {
        }
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        public TextView tv_content;
        public TextView tv_title;
        public ImageView iv_abstract_pic;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_foot;

        public FootViewHolder(View view) {
            super(view);
            tv_item_foot = (TextView) view.findViewById(R.id.tv_item_foot);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String data);
    }
}
