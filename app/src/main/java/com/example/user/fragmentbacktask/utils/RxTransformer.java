package com.example.user.fragmentbacktask.utils;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huangyuxi on 2019/3/24
 * Title:
 */
public class RxTransformer {
    public static <T> FlowableTransformer<T, T> flowableTransformer() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
