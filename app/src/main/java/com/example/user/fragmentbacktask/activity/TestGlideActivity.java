package com.example.user.fragmentbacktask.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;
import com.example.user.fragmentbacktask.adapter.TurnAdAdapter;
import com.example.user.fragmentbacktask.db.DbUtils;
import com.example.user.fragmentbacktask.entity.MoiveBean;
import com.example.user.fragmentbacktask.utils.ScreenUtils;
import com.example.user.fragmentbacktask.view.AutoScrollViewPager;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class TestGlideActivity extends AppCompatActivity {

    private AutoScrollViewPager vp_turn_ad;
    private LinearLayout ll_ad_mark;
    private TextView tv_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_glide);
//        RefWatcher refWatcher = TestApplication.getRefWatcher(this);
//        refWatcher.watch(this);
        initViews();
        fetchIntent();
        initData();
    }

    private void initViews() {
        vp_turn_ad = (AutoScrollViewPager) findViewById(R.id.vp_turn_ad);
        ll_ad_mark = (LinearLayout) findViewById(R.id.ll_ad_mark);
        tv_intent = (TextView) findViewById(R.id.tv_intent);
    }


    private void fetchIntent() {
    }

    private void initData() {
        setAdData(vp_turn_ad,ll_ad_mark);
    }


    private void setAdData(AutoScrollViewPager viewpager, final LinearLayout markContainer) {
        String adurl1 = "http://js.soufunimg.com/jinrong/dai/wap/Images/zhuanti/ZiJinTuoGuan.jpg?v=1.0";
        String adurl2 = "http://js.soufunimg.com/jinrong/dai/wap/Images/zhuanti/wkdz_banner.jpg?v=1.0";
        ArrayList<String> list = new ArrayList<>(Arrays.asList(adurl1, adurl2));
        initImg(markContainer, 2);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("onPageSelected",position+"");
//                changePosition(position,markContainer);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置viewpager自动滑动时间
        viewpager.setInterval(3000);
        viewpager.startAutoScroll(3000);
        viewpager.setScrollDurationFactor(2);
        TurnAdAdapter adpter = new TurnAdAdapter(TestGlideActivity.this, list);
        viewpager.setAdapter(adpter);

    }

    private void initImg(LinearLayout markContainer, int num) {
        markContainer.removeAllViews();
        if (num != 1) {
            for (int i = 0; i < num; i++) {
                ImageView img = new ImageView(this);
                img.setImageResource(R.mipmap.ad_switcher_btn);
                if (ScreenUtils.screenWidth <= 480) {
                    img.setPadding(10, 0, 0, 0);
                } else {
                    img.setPadding(25, 0, 0, 0);
                }
                markContainer.addView(img);
            }
            changePosition(0, markContainer);
        }
    }

    private ImageView currentImg;// 当前选中的小圆点
    private int flagNum = -1;

    protected void changePosition(int position, LinearLayout markContainer) {
        if(flagNum != position) {
            flagNum = position;
            if (currentImg != null)
                currentImg.setImageResource(R.mipmap.ad_switcher_btn);

            currentImg = (ImageView) markContainer.getChildAt(position);
            if (currentImg == null)
                return;
            currentImg.setImageResource(R.mipmap.ad_switcher_btn_selected);
        }
    }

}
