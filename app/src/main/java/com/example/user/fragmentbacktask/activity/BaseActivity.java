package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        initCreate(savedInstanceState);
    }

    public abstract int provideLayoutId();

    public abstract void initCreate(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
