package com.example.user.fragmentbacktask.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by huangyuxi on 2019-06-11
 * Title:
 */
public class LocalWorker extends Worker {

    public LocalWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        // 当任务结束时会回调这里
    }

    @NonNull
    @Override
    public Result doWork() {
        //  Data outputData
        // 任务执行完毕


        return Worker.Result.success();
    }
}
