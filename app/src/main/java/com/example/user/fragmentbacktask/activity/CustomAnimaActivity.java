package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.fragmentbacktask.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomAnimaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_expand, btn_textview;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_anima);
        mContext = this;
        initView();
    }

    private void initView() {
        btn_expand = (Button) findViewById(R.id.btn_expand);
        btn_expand.setOnClickListener(this);
        btn_textview = (Button) findViewById(R.id.btn_textview);
        btn_textview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_expand:
                Intent intent = new Intent(mContext, TestAnimaActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_textview:
                Intent intentList = new Intent(mContext, AnimaTextViewActivity.class);
                startActivity(intentList);
                break;
        }
    }
}