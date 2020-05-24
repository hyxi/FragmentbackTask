package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;

public class LayoutOptActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_opt);
        initView();

//        LayoutInflater.from(this).inflate(R.layout.merge_test,
//                (RelativeLayout)findViewById(R.id.activity_layout_opt),true);
//
        //viewstub

//        findViewById(R.id.tv_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext , "点击",Toast.LENGTH_LONG).show();
//            }
//        });

        //多个include 解决方案

    /** 1)..int[] id = {R.id.item1,R.id.item2}
        for(int i  = 0;i<id.length;i++){
            View v = findViewById(id[i]);
            TextView tv = (TextView) v.findViewById(R.id.sub_tv);
            tv.setId(i);
            tv.setText("测试 " + (i + 1));
        }
     2)..  上下显示
     layout_width
     layout_height
     layout_below
     */

        mContext = this;
    }

    private void initView() {
        TextView tv_fir = (TextView) findViewById(R.id.tv_first);
//        tv_fir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext , "点击",Toast.LENGTH_LONG).show();
//            }
//        });


    }
}
