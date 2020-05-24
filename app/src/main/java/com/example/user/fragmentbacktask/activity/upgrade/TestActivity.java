package com.example.user.fragmentbacktask.activity.upgrade;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.TestAdapter;

import java.util.ArrayList;

public class TestActivity extends Activity implements View.OnClickListener {

    private TextView add, change;
    private RecyclerView rv_list;
    private ArrayList<String> list;
    private TestAdapter adapter;
    private LinearLayout rooview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        rooview = (LinearLayout) findViewById(R.id.activity_test);
        add = (TextView) findViewById(R.id.add);
        change = (TextView) findViewById(R.id.change);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        add.setOnClickListener(this);
        change.setOnClickListener(this);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("content " + i);
        }


        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        manager.setStackFromEnd(true);
        rv_list.setLayoutManager(manager);
//        adapter = new TestAdapter(this);
//        adapter.addDatas(list);
//
//        View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_test_layout,rooview,false);
//        adapter.setHeaderView(headerView);
//
//        rv_list.setAdapter(adapter);
    }

    int count = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                count++;
                String s = "add" + count;
                list.add(s);
                adapter.notifyItemInserted(adapter.getItemCount()-1);
                adapter.notifyItemRangeChanged(adapter.getItemCount()-1,1);

                //adapter.notifyDataSetChanged();
                break;
            case R.id.change:
                break;
        }
    }
}
