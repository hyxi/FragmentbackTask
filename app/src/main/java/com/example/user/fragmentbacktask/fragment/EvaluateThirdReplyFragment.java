package com.example.user.fragmentbacktask.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.TestAdapter;
import com.example.user.fragmentbacktask.entity.TestBean;
import com.example.user.fragmentbacktask.http.RxComposetion;
import com.example.user.fragmentbacktask.utils.RxTransformer;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2016/1/12.
 */
public class EvaluateThirdReplyFragment extends RxFragment {

    public String TAG = "EvaluateThirdReplyFragment";
    private TextView tv_content;
    private CompositeDisposable mCompositeDisposable;

    private RecyclerView rvList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "on onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "on onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_replay, container, false);
        if (getArguments() != null) {
            tv_content = view.findViewById(R.id.tv_content);
            String content = getArguments().getString("name");
//            tv_content.setText(content);
        }
        rvList = view.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);

        initData();

        mCompositeDisposable = new CompositeDisposable();
        Log.i(TAG, "on onCreateView");
        return view;
    }

    private void initData() {
        TestAdapter mAdapter = new TestAdapter(getContext());
        List<TestBean> list = new ArrayList<>();
        String[] contentArray = new String[]{"resume", "pause", "start", "onIntent", "ondestroy"};
        for (int i = 0; i < 20; i++) {
            int randomIndex = new Random().nextInt(4);
            list.add(new TestBean(i, contentArray[randomIndex] + "i"));
        }
        mAdapter.addDatas(list);
        rvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on onResume");


        Flowable<Integer> flowable1 = Flowable.just(1, 2)
                .compose(RxTransformer.flowableTransformer());
        Flowable<Integer> flowable2 = Flowable.just(3, 5, 7);

//        conbinelatest(flowable1, flowable2);

//        comsetion(flowable1, flowable2);
//        Observable.interval();

        // 嵌套 网络请求
//        nestRequest(flowable1, flowable2);

//        connect();
        int maxMemory = (int) (Runtime.getRuntime().totalMemory() / 1024);
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        takeUtil();

        Disposable originDispose = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).map(new Function<String, Object>() {
            @Override
            public Object apply(String s) throws Exception {
                return null;
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {

                    }
                });

        Disposable viewDispose = RxView.clicks(tv_content)
                .throttleFirst(1, TimeUnit.SECONDS) // 1s 发送一次按钮点击事件
                .compose(bindToLifecycle())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Logger.d("点击view");
                });
    }

    private void nestRequest(Flowable<Integer> flowable1, Flowable<Integer> flowable2) {
        flowable1.doOnNext(result1 -> {
            Logger.d("内容：" + result1);
        }).observeOn(Schedulers.io())
                .flatMap(new Function<Integer, Flowable<Integer>>() {
                    @Override
                    public Flowable<Integer> apply(Integer result1) throws Exception {
                        return flowable2;
                    }
                }).compose(RxTransformer.flowableTransformer())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer response) throws Exception {
                        Logger.d("第二个请求：" + response);
                    }
                });
    }

    private void takeUtil() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        if (integer == 3) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("take:" + integer);
            }
        });
    }

    private void connect() {
        ConnectableFlowable<Long> interval = Flowable.interval(2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Logger.d("time:" + aLong);
                    }
                })
                .publish();
        interval.share().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Logger.d("time subscribe:" + aLong);
            }
        });
    }

    private void comsetion(Flowable<Integer> flowable1, Flowable<Integer> flowable2) {
        Disposable disposable1 = flowable1.subscribe(response -> {
            Logger.d("内容：" + response);
        }, throwable -> {
            Logger.d(throwable.toString());
        });

        Disposable disposable2 = Flowable.zip(flowable1, flowable2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer t1, Integer t2) {
                return t1 + t2;
            }
        })
                .subscribe(data -> {
                    Logger.e("内容：" + data);
                });

        RxComposetion.addSubscribe(mCompositeDisposable, disposable1);
        RxComposetion.addSubscribe(mCompositeDisposable, disposable2);
    }

    private void conbinelatest(Flowable<Integer> flowable1, Flowable<Integer> flowable2) {
        Flowable.combineLatest(flowable1, flowable2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.d("result:" + integer);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "on onStop");
    }

    @Override
    public boolean getUserVisibleHint() {
        Log.i(TAG, "on getUserVisibleHint");
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "on setUserVisibleHint//" + isVisibleToUser);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "on onDestroyView");
        super.onDestroyView();
        RxComposetion.unSubscribe(mCompositeDisposable);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "on detach");
        super.onDetach();
    }
}
