package com.example.user.fragmentbacktask.viewmodel;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.util.Random;

/**
 * Created by huangyuxi on 2019-06-12
 */
public class LocalService extends Service {

    public static final String TAG = LocalService.class.getName();

    private MyBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(TAG + " onBind");
        return binder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d(TAG + " onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG + " onDestroy");
    }

    public int getRandomNumber() {
        Logger.d(TAG + " getRandomNumber");
        return new Random().nextInt();
    }

    public class MyBinder extends Binder {

        public LocalService getService() {
            return LocalService.this;
        }

        public int invokeMethodInMyService(int a, int b) {
            return a + b;
        }
    }

}
