package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.fragmentbacktask.BlurPictureActivity;
import com.example.user.fragmentbacktask.R;

import androidx.appcompat.app.AppCompatActivity;

public class BitmapRelatedActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_chat, btn_stack_blur;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_related);
        mContext = this;
        initView();
    }

    private void initView() {
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(this);
        btn_stack_blur = (Button) findViewById(R.id.btn_stack_blur);
        btn_stack_blur.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat:
                Intent intent = new Intent(mContext, TestBitmapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_stack_blur:
                Intent intentList = new Intent(mContext, BlurPictureActivity.class);
                startActivity(intentList);
                break;
        }
    }
}
