package com.example.user.fragmentbacktask.http;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by huangyuxi on 2019/3/24
 * Title:
 */
public class RxComposetion {

    public static void addSubscribe(CompositeDisposable compositeDisposable, Disposable disposable) {
        if (compositeDisposable != null && disposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public static void unSubscribe(CompositeDisposable compositeSubscription) {
        if (compositeSubscription != null && !compositeSubscription.isDisposed()) {
            compositeSubscription.dispose();
        }
    }
}
