package com.example.user.fragmentbacktask.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.ObservableScrollView;
import com.example.user.fragmentbacktask.view.OnScrollChangedCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    ListView lv_content;
    List<String> simulateData;
    ObservableScrollView sv_observable;
    ImageView iv_header;
    View header_bg;
    FrameLayout fl_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initview();
        initData();
        setListener();
    }

    private void initview() {
        lv_content = (ListView) findViewById(R.id.lv_content);
        sv_observable = (ObservableScrollView) findViewById(R.id.sv_observable);
        iv_header = (ImageView) findViewById(R.id.iv_header);
        header_bg = findViewById(R.id.header_bg);
        fl_header = (FrameLayout) findViewById(R.id.fl_header);
        header_bg.setAlpha(0);
    }

    private void initData() {
        String[] datas = {"American Museum of Natural History", "Apollo Theater", "Bank of America Tower",
                "Battery Park", "Rose Center forEarth Monument", "Castle Clinton", "The Sphere", "Brill Building",
                "East Coast Memorial", "Fadingdemo", "Battery Park", "Rose Center forEarth Monument", "Castle Clinton", "The Sphere",
                "Brill Building"};
        simulateData = new ArrayList<>(Arrays.asList(datas));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, simulateData);
        lv_content.setAdapter(adapter);
        lv_content.setFocusable(false);
    }

    private void setListener() {
        sv_observable.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                int headerHeight = iv_header.getHeight()- fl_header.getHeight();
                float ratio  = (float)Math.min(Math.max(t,0),headerHeight)/headerHeight;
                if(headerHeight>t){
                    header_bg.setAlpha(ratio);
                }else {
                    header_bg.setAlpha(1);
                }
            }
        });
    }

}
