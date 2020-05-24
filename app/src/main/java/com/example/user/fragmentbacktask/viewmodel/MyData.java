package com.example.user.fragmentbacktask.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;
import androidx.paging.TiledDataSource;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyuxi on 2019-06-11
 * Title:
 */
public class MyData extends LiveData<String> {

    private static final String TAG = "T-MyData";

    @Override
    protected void onActive() {
        super.onActive();
        Logger.d(TAG + " onActive");

        new ItemKeyedDataSource<Integer, Object>() {
            int page = 1;
            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Object> callback) {

            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Object> callback) {

            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Object> callback) {

            }

            @NonNull
            @Override
            public Integer getKey(@NonNull Object item) {
                return page;
            }
        };

        PositionalDataSource<Object> rangeSource = new PositionalDataSource<Object>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {

            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Object> callback) {
                List<Object> dataList = new ArrayList<>();

                try{

                }catch (Exception e) {
                    e.printStackTrace();
                }
                callback.onResult(dataList);
            }
        };

        DataSource.Factory<Integer, Object> dataSource = new DataSource.Factory<Integer, Object>() {
            @Override
            public DataSource<Integer, Object> create() {
                return rangeSource;
            }
        };

        PagedList.Config builder = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false) // 启动placeholder
                .setInitialLoadSizeHint(10)
                .build();
       LiveData<PagedList<Object>> liveData = new LivePagedListBuilder(dataSource, builder)
                .build();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Logger.d(TAG + " onInactive");
    }

    public void changeValue(String value) {
        setValue(value);
    }
}
