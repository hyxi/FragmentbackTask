package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.activity.viewactivity.ListRefreshActivity;
import com.example.user.fragmentbacktask.activity.viewactivity.ScrollContainerActivity;
import com.example.user.fragmentbacktask.activity.viewactivity.SlideActivity;
import com.example.user.fragmentbacktask.activity.viewactivity.TestDrawActivity;
import com.example.user.fragmentbacktask.activity.viewactivity.VpFrameActivity;

public class CustomViewActivity extends AppCompatActivity implements View.OnClickListener{

    private Button jump_scroll_container_btn,jump_listview_btn,btn_recycle,draw_test
            ,jump_scroll_navlayout_btn,btn_Layout,btn_vp_framelayout;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mContext = this;
        initView();
    }

    private void initView() {
        jump_scroll_container_btn = (Button) findViewById(R.id.jump_scroll_container_btn);
        jump_scroll_container_btn.setOnClickListener(this);
        jump_listview_btn = (Button) findViewById(R.id.jump_listview_btn);
        jump_listview_btn.setOnClickListener(this);

        btn_recycle = (Button) findViewById(R.id.btn_recycle);
        btn_recycle.setOnClickListener(this);

        draw_test = (Button) findViewById(R.id.btn_draw_test);
        draw_test.setOnClickListener(this);

        jump_scroll_navlayout_btn = (Button) findViewById(R.id.jump_scroll_navlayout_btn);
        jump_scroll_navlayout_btn.setOnClickListener(this);

        btn_Layout = (Button) findViewById(R.id.btn_Layout);
        btn_Layout.setOnClickListener(this);

        btn_vp_framelayout = (Button) findViewById(R.id.btn_vp_framelayout);
        btn_vp_framelayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_scroll_container_btn:
                Intent intent = new Intent(mContext,ScrollContainerActivity.class);
                startActivity(intent);
                break;
            case  R.id.jump_listview_btn:
                Intent intentList = new Intent(mContext,ListRefreshActivity.class);
                startActivity(intentList);
            break;
            case  R.id.btn_recycle:
                Intent intentRec = new Intent(mContext,RecycleViewActivity.class);
                startActivity(intentRec);
                break;
            case  R.id.jump_scroll_navlayout_btn:
                Intent intentNav = new Intent(mContext,BounceNavLayoutActivity.class);
                startActivity(intentNav);
                break;
            case  R.id.btn_draw_test:
                Intent intentTest = new Intent(mContext,TestDrawActivity.class);
                startActivity(intentTest);
                break;
            case  R.id.btn_Layout:
                Intent intentSlide = new Intent(mContext,SlideActivity.class);
                startActivity(intentSlide);
                break;
            case  R.id.btn_vp_framelayout:
                Intent intentVpf = new Intent(mContext,VpFrameActivity.class);
                startActivity(intentVpf);
                break;
        }
    }
}
