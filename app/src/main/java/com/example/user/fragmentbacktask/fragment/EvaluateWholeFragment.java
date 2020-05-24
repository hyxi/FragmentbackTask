package com.example.user.fragmentbacktask.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.activity.BitmapRelatedActivity;
import com.example.user.fragmentbacktask.activity.ChatInputBoardActivity;
import com.example.user.fragmentbacktask.activity.CustomAnimaActivity;
import com.example.user.fragmentbacktask.activity.CustomTabLayoutActivity;
import com.example.user.fragmentbacktask.activity.CustomViewActivity;
import com.example.user.fragmentbacktask.activity.PinyinActivity;
import com.example.user.fragmentbacktask.activity.map.BaiduMapStuActivity;
import com.example.user.fragmentbacktask.base.BaseFragment;
import com.example.user.fragmentbacktask.view.DaggerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/1/12.
 */
public class EvaluateWholeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.jump_tablayout_btn)
    Button jumpTablayoutBtn;
    @BindView(R.id.jump_to_custome_view)
    Button jumpToCustomeView;
    @BindView(R.id.btn_map_view)
    Button btnMapView;
    @BindView(R.id.jump_to_animator)
    Button jumpToAnimator;
    @BindView(R.id.btn_bitmap)
    Button btnBitmap;
    @BindView(R.id.btn_layout_opt)
    Button btnLayoutOpt;
    @BindView(R.id.btn_test)
    Button btnTest;

    @Override
    public int layoutId() {
        return R.layout.fragment_evaluate_whole;
    }

    @Override
    public void initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        jumpTablayoutBtn.setOnClickListener(this);
        jumpToCustomeView.setOnClickListener(this);
        btnMapView.setOnClickListener(this);
        jumpToAnimator.setOnClickListener(this);
        btnBitmap.setOnClickListener(this);
        btnLayoutOpt.setOnClickListener(this);
        btnTest.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jump_to_custome_view:
                startActivity(new Intent(mContext, CustomViewActivity.class));
                break;
            case R.id.jump_to_animator:
                startActivity(new Intent(mContext, CustomAnimaActivity.class));
                break;
            case R.id.jump_tablayout_btn:
                startActivity(new Intent(mContext, CustomTabLayoutActivity.class));
                break;
            case R.id.btn_bitmap:
                startActivity(new Intent(mContext, BitmapRelatedActivity.class));
                break;
            case R.id.btn_map_view:
                startActivity(new Intent(mContext, BaiduMapStuActivity.class));
                break;
            case R.id.btn_test:
                startActivity(new Intent(mContext, PinyinActivity.class));
                break;
            case R.id.btn_layout_opt:
                startActivity(new Intent(mContext, DaggerActivity.class));
                break;
        }
    }
}
