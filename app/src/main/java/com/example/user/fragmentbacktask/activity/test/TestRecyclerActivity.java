package com.example.user.fragmentbacktask.activity.test;

import android.content.Context;
import android.os.Bundle;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.TestAdapter;
import com.example.user.fragmentbacktask.entity.TestBean;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestRecyclerActivity extends AppCompatActivity {

    private RecyclerView rv_test;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler);
        mContext = this;
        rv_test = (RecyclerView) findViewById(R.id.rv_test);

        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        rv_test.setLayoutManager(manager);

        initData();
    }

    private void initData() {
        ArrayList<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            TestBean tb = new TestBean();
            tb.setId(i);
            if(i == 5 || i == 10){
                tb.setContent("回答：部分机型，浏览页面时打开视频播放，点击返回，页面上的视频仍然在播放。解决办法是捕获后退事件，主动调用 onHideCustomView() 方法，型，浏览页面时打开视频播放，点击返回，页面上的视频仍然在播放。解决办法是捕获后退事件，主动调用 onHideCustomView() 方法，并且在该方法里将 onShowCustomView 里关联的view解除关联 " + i);
            }else{
                tb.setContent("content "+i);
            }

            list.add(tb);
        }
        TestAdapter adapter = new TestAdapter(this);
        adapter.addDatas(list);
        rv_test.setAdapter(adapter);
    }
}
