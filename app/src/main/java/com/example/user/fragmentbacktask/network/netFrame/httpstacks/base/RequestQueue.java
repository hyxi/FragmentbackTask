package com.example.user.fragmentbacktask.network.netFrame.httpstacks.base;

import com.example.user.fragmentbacktask.network.netFrame.httpstacks.HttpStack;
import com.example.user.fragmentbacktask.network.netFrame.httpstacks.HttpStackFactory;
import com.example.user.fragmentbacktask.network.netFrame.httpstacks.NetworkExecutor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user on 2016/8/18.
 */
public final class RequestQueue {

    //请求队列
    private final PriorityBlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();
    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    private final Set<Request<?>> mCurrentRequests = new HashSet<>();

    /**
     * 默认的核心数
     */
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * CPU核心数 + 1个分发线程数
     */
    private int mDispatcherNums = 4;

    /**
     * NetworkExecutor,执行网络请求的线程
     */
    private NetworkExecutor[] mDispatchers = null;
    /**
     * Http请求的真正执行者
     */
    private HttpStack mHttpStack;

    /**
     * @param coreNums 线程核心数
     */
    protected RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    public <T> Request<T> add(Request<T> request) {

        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }
        return request;
    }

}
