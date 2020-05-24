package com.example.user.fragmentbacktask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.utils.KeepLiveManager;

/**
 * Created by huangyuxi on 2019-04-28
 * Title:
 */
public class InnerService extends Service {

    private Service mKeepLiveService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mKeepLiveService != null) {
            KeepLiveManager.getInstance().serForeground(mKeepLiveService, this);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
