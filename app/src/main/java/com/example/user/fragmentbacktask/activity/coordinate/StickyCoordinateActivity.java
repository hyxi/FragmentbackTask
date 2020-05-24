package com.example.user.fragmentbacktask.activity.coordinate;

import android.os.Bundle;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.RecycleAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StickyCoordinateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_coordinater);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        initData();
    }

    private void initData() {
        ArrayList<String> mDatas = new ArrayList<>();
        for(int i =0;i<100;i++){
            mDatas.add("content data"+ i);
        }
        RecycleAdapter adapter = new RecycleAdapter(this, mDatas);
        final LinearLayoutManager manager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
