package com.example.user.fragmentbacktask.activity;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.PictureAdapter;
import com.example.user.fragmentbacktask.entity.PictureBean;
import com.example.user.fragmentbacktask.view.FlowScrollview;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class FlowActivity extends AppCompatActivity {

    private FlowScrollview img_flow_scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        initView();
        initData();
    }

    private void initView() {
        img_flow_scrollview = (FlowScrollview) findViewById(R.id.img_flow_scrollview);


    }

    private void initData() {

        ValueAnimator animator = new ValueAnimator();
        TimeInterpolator interpolator = new DecelerateInterpolator(2);
        animator.setInterpolator(interpolator);

        ArrayList<PictureBean> list = new ArrayList<>();
        int[] imgRes = {R.mipmap.png_01,R.mipmap.png_02,R.mipmap.png_03,
                R.mipmap.png_04,R.mipmap.png_05,R.mipmap.png_06,
                R.mipmap.png_01,R.mipmap.png_02,R.mipmap.png_03};
        for(int i =0;i<imgRes.length;i++){
            list.add(new PictureBean(imgRes[i], "test0"+i));
        }
        PictureAdapter mAdapter = new PictureAdapter(this, list);
        img_flow_scrollview.setAdapter(mAdapter);
    }

}
