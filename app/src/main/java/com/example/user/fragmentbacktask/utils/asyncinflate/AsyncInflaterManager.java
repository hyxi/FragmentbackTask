package com.example.user.fragmentbacktask.utils.asyncinflate;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangyuxi on 2019-09-12
 */
public class AsyncInflaterManager {

    public static final String TAG = "AsyncInflaterManager";

    private volatile static AsyncInflaterManager sInstance;
    private ConcurrentHashMap<String, AsyncInflateItem> mInflaterMap;
    private ConcurrentHashMap<String, CountDownLatch> mInflaterLatchMap;
    private ExecutorService mThreadPool;

    private AsyncInflaterManager() {
        mThreadPool = new ThreadPoolExecutor(4, 4, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));
        mInflaterMap = new ConcurrentHashMap<>();
        mInflaterLatchMap = new ConcurrentHashMap<>();
    }

    public static AsyncInflaterManager getInstance() {
        if (sInstance == null) {
            synchronized (AsyncInflaterManager.class) {
                if (sInstance == null) {
                    sInstance = new AsyncInflaterManager();
                }
            }
        }
        return sInstance;
    }

    @UiThread
    @NonNull
    public View getInflatedView(Context context, int layoutResId, @Nullable ViewGroup parent,
                                String inflateKey, @NonNull LayoutInflater layoutInflater) {

        if (!TextUtils.isEmpty(inflateKey) && mInflaterMap.containsKey(inflateKey)) {
            AsyncInflateItem item = mInflaterMap.get(inflateKey);
            CountDownLatch countLatch = mInflaterLatchMap.get(inflateKey);
            if (item != null) {
                View resultView = item.getInfaltedView();
                if (resultView != null) {
                    removeInflateKey(inflateKey);
                    replaceContextForView(resultView, context);
                    return resultView;
                }

                if (item.isInflating() && countLatch != null) {
                    //没拿到view，但是在inflate中，等待返回
                    try {
                        countLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resultView = item.getInfaltedView();
                    removeInflateKey(inflateKey);
                    if (resultView != null) {
                        replaceContextForView(resultView, context);
                        return resultView;
                    }
                }
                //如果还没开始inflate，则设置为false，UI线程进行inflate
                item.setCancelled(true);
            }
        }
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    @UiThread
    public void asyncInflate(Context context, AsyncInflateItem item) {
        if (item == null || item.getLayoutResId() == 0
                || mInflaterMap.containsKey(item.getInflateKey())
                || item.isCancelled()
                || item.isInflating()) {
            return;
        }
        onAsyncInflateReady(item);
        inflateWithThreadPool(context, item);
    }

    private void onAsyncInflateReady(AsyncInflateItem item) {
    }

    private void onAsyncInflateStart(AsyncInflateItem item) {
        item.setInflating(true);
        mInflaterLatchMap.put(item.getInflateKey(), new CountDownLatch(1));
        mInflaterMap.put(item.getInflateKey(), item);
    }

    private void onAsyncInflateEnd(AsyncInflateItem item, boolean success) {
        if (!success) {
            mInflaterMap.remove(item.getInflateKey());
            mInflaterLatchMap.remove(item.getInflateKey());
            return;
        }
        item.setInflating(false);
        CountDownLatch latch = mInflaterLatchMap.get(item.getInflateKey());
        if (latch != null) {
            latch.countDown();
        }
    }

    private void inflateWithThreadPool(Context context, AsyncInflateItem item) {
        mThreadPool.execute(() -> {
            if (!item.isInflating() && !item.isCancelled()) {
                try {
                    onAsyncInflateStart(item);
                    View view = new BasicInflater(context).inflate(item.getLayoutResId(), item.getParent(), false);
                    item.setInfaltedView(view);
                    onAsyncInflateEnd(item, true);
                } catch (RuntimeException e) {
                    Log.e(TAG, "Failed to inflate resource in the background! Retrying on the UI thread", e);
                    onAsyncInflateEnd(item, false);
                }
            }
        });
    }

    /**
     * copy from AsyncLayoutInflater - actual inflater
     */
    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.webkit.", "android.app."};

        BasicInflater(Context context) {
            super(context);
        }

        public LayoutInflater cloneInContext(Context newContext) {
            return new BasicInflater(newContext);
        }

        protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
            for (String prefix : sClassPrefixList) {
                try {
                    View view = this.createView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }
            return super.onCreateView(name, attrs);
        }
    }

    private void removeInflateKey(String inflateKey) {
        mInflaterMap.remove(inflateKey);
        mInflaterLatchMap.remove(inflateKey);
    }

    /**
     * inflater初始化时是传进来的application，inflate出来的view的context没法用来startActivity，
     * 因此用MutableContextWrapper进行包装，后续进行替换
     */
    private void replaceContextForView(View inflatedView, Context context) {
        if (inflatedView == null || context == null) {
            return;
        }
        Context cxt = inflatedView.getContext();
        if (cxt instanceof MutableContextWrapper) {
            ((MutableContextWrapper) cxt).setBaseContext(context);
        }
    }
}
