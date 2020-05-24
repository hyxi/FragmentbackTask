package com.example.user.fragmentbacktask.activity.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.fragmentbacktask.R;

public class BaiduMapStuActivity extends Activity implements View.OnClickListener {

    Button btn_map_search,btn_map_route;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map_stu);
        mContext = this;
        btn_map_search = (Button) findViewById(R.id.btn_map_search);
        btn_map_search.setOnClickListener(this);

        btn_map_route = (Button) findViewById(R.id.btn_map_route);
        btn_map_route.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_map_search:
                startActivity(new Intent(mContext, BMapSearchActivity.class));
                break;
            case R.id.btn_map_route:
                startActivity(new Intent(mContext, BMapRouteActivity.class));
                break;
        }
    }
}
