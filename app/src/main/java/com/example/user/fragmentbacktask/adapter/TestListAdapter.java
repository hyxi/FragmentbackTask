package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.viewpager.widget.PagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.CommentList;
import com.example.user.fragmentbacktask.utils.BitmapUtils;
import com.example.user.fragmentbacktask.utils.ImageUtils;
import com.example.user.fragmentbacktask.utils.StringUtils;

import java.util.ArrayList;

/**
 * huangyx
 */
public class TestListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CommentList> commentList;
    private LayoutInflater mInflater;

    private OnRespClickListener mListener;

    public TestListAdapter(Context mContext, ArrayList<CommentList> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setOnRepClickListener(OnRespClickListener listener) {
        this.mListener = listener;
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
        holder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
        holder.ll_response = (LinearLayout) convertView.findViewById(R.id.ll_response);

        holder.tv_content.setText(commentList.get(position).getContent());

        ArrayList<String> resps = commentList.get(position).getList();
        if (resps != null && resps.size() != 0) {
            initResponseData(holder.ll_response, resps);
        } else {
            holder.ll_response.setVisibility(View.GONE);
        }
        return convertView;
    }


    private void initResponseData(LinearLayout layout, ArrayList<String> list) {
        layout.setVisibility(View.VISIBLE);
        layout.removeAllViews();

        for (String respMsg : list) {
            TextView textView = new TextView(mContext);
            textView.setTextSize(14);
            textView.setTextColor(Color.parseColor("#888888"));
            textView.setBackgroundResource(R.drawable.text_reponse_bg);
            SpannableString spanStr = new SpannableString("张三回复：" + respMsg);
            spanStr.setSpan(new ForegroundColorSpan(Color.parseColor("#3668b2")), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spanStr);

            int padding = StringUtils.dp2px(3);
            textView.setPadding(0,padding,0,padding);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(params);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    int height = location[1] + view.getBottom() - view.getTop();
                    if (mListener != null)
                        mListener.onRespClick(height);
                }
            });

            layout.addView(textView);
        }
    }

    public interface OnRespClickListener {
        void onRespClick(int anchor);
    }


    private class ViewHolder {
        TextView tv_content;
        ImageView iv_picture;
        LinearLayout ll_response;
    }

}
