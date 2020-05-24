package com.example.user.fragmentbacktask.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

/**
 * Created by huangyuxi on 2019-06-11
 * Title:
 */
public class SharedViewModel extends AndroidViewModel {

   private MyData data;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.data = new MyData();
    }

    public MyData getData() {
        return data;
    }
}
