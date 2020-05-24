package com.example.user.fragmentbacktask.http;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * Created by huangyuxi on 2019/3/24
 * Title:
 */
public class RxjavaView extends Observable<Object> {
    private final View view;

    RxjavaView(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        Listener listener = new Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final View view;
        private final Observer<? super Object> observer;

        Listener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override public void onClick(View v) {
            if (!isDisposed()) {
                observer.onNext("aaaa");
            }
        }

        @Override protected void onDispose() {
            view.setOnClickListener(null);
        }
    }
}
