package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;


/**
 * Created by burke
 */
public class BaseLayout extends RelativeLayout {

    private Context mContext;

    private View header_bar;
    public ImageView iv_back;
    public TextView tv_title, tv_right,tv_load_error;
    public View progressbg;
    public PageLoadingView plv_loading;
    public Button btn_refresh;

    private static final int HEADER = 1;
    private static final int PROGRESS = 2;

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLayout(Context context, int layoutResourceId,int type) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case HEADER:
                setHeaderBar(layoutInflater);
                break;
            case PROGRESS:
                setHeaderBar(layoutInflater);
                setProgressBg(layoutInflater);
                break;
        }

        View view = layoutInflater.inflate(layoutResourceId, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        if(progressbg != null) {
            params.addRule(RelativeLayout.BELOW, R.id.process_page);
        }else{
            params.addRule(RelativeLayout.BELOW, R.id.header_bar);
        }
        addView(view, params);
    }

    protected void setHeaderBar(LayoutInflater layoutInflater) {
        header_bar = layoutInflater.inflate(R.layout.header_bar, null);
        iv_back = (ImageView) header_bar.findViewById(R.id.iv_back);
        tv_title = (TextView) header_bar.findViewById(R.id.tv_title);
        tv_right = (TextView) header_bar.findViewById(R.id.tv_right);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(header_bar, params);
    }

    public void setTitleAndButton(String title, String right) {
        if (!StringUtils.isNullOrEmpty(title)) {
            tv_title.setText(title);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        if (StringUtils.isNullOrEmpty(right)) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(right);
        }
    }


    protected void setProgressBg(LayoutInflater layoutInflater) {
        progressbg = layoutInflater.inflate(R.layout.process_page, null);
        progressbg.setId(R.id.process_page);
        plv_loading = (PageLoadingView) progressbg
                .findViewById(R.id.plv_loading);
        tv_load_error = (TextView) progressbg.findViewById(R.id.tv_load_error);
        btn_refresh = (Button) progressbg.findViewById(R.id.btn_refresh);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.header_bar);
        addView(progressbg, params);
    }

}
