package com.example.user.fragmentbacktask.chart;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import dalvik.system.DexClassLoader;

/**
 * Created by huangyuxi on 2019-08-22
 * Title:
 */
public class HandleService extends IntentService {

    public HandleService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DexClassLoader classLoader = (DexClassLoader) intent.getClass().getClassLoader();


    }
}
