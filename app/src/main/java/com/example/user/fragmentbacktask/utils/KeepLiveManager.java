package com.example.user.fragmentbacktask.utils;

import android.app.Notification;
import android.app.Service;
import android.os.Build;

/**
 * Created by huangyuxi on 2019-04-28
 * Title:
 */
public class KeepLiveManager {
    private static class InstanceHolder {
        public static KeepLiveManager singleton = new KeepLiveManager();
    }

    private static KeepLiveManager instance;

    public static KeepLiveManager getInstance() {
        if (instance == null) {
            instance = InstanceHolder.singleton;
        }
        return instance;
    }

    public void serForeground(final Service keepLiveService, final Service innerService) {
        final int foregroundPushId = 1;
        if (keepLiveService != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                keepLiveService.startForeground(foregroundPushId, new Notification());
            } else {
                keepLiveService.startForeground(foregroundPushId, new Notification());
                if (innerService != null) {
                    innerService.startForeground(foregroundPushId, new Notification());
                    innerService.stopSelf();
                }
            }
        }
    }

}
