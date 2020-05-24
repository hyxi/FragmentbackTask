package com.example.user.fragmentbacktask.network.netFrame.httpstacks.base;

import java.util.HashMap;
import java.util.Map;

/**
 *  网络请求类. 注意GET和DELETE不能传递请求参数,因为其请求的性质所致,
 *  用户可以将参数构建到url后传递进来到Request中.
 *
 * @param <T> T为请求返回的数据类型 .
 */
public abstract class Request<T> implements Comparable<Request<T>> {
    /**
     * http请求方法枚举,这里我们只有GET, POST, PUT, DELETE四种
     *
     * @author mrsimple
     */
    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        /** http request type */
        private String mHttpMethod = "";

        private HttpMethod(String method) {
            mHttpMethod = method;
        }

        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    /**
     * 优先级枚举
     *
     * @author mrsimple
     */
    public static enum Priority {
        LOW,
        NORMAL,
        HIGN,
        IMMEDIATE
    }
    /**
     * 是否取消该请求
     */
    protected boolean isCancel = false;

    /** 该请求是否应该缓存 */
    private boolean mShouldCache = true;
    /**
     * 优先级默认设置为Normal
     */
    protected Priority mPriority = Priority.NORMAL;
    /**
     * 请求Listener
     */
    protected RequestListener<T> mRequestListener;
    /**
     * 请求的url
     */
    private String mUrl = "";

    /**
     * 请求序列号
     */
    protected int mSerialNum = 0;
    /**
     * 请求的方法
     */
    HttpMethod mHttpMethod = HttpMethod.GET;
    /**
     * 请求的header
     */
    private Map<String, String> mHeaders = new HashMap<String, String>();
    /**
     * 请求参数
     */
    private Map<String, String> mBodyParams = new HashMap<String, String>();

    /**
     * @param method
     * @param url
     * @param listener
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        mHttpMethod = method;
        mUrl = url;
        mRequestListener = listener;
    }

    /**
     * 处理Response,该方法运行在UI线程.
     *
     * @param response
     */
    public final void deliveryResponse(Response response) {
        // 解析得到请求结果
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unkown error";
            mRequestListener.onComplete(stCode, result, msg);
        }
    }

    abstract protected T parseResponse(Response response);

    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority mPriority) {
        this.mPriority = mPriority;
    }

    public int getSerialNumber() {
        return mSerialNum;
    }

    public void setSerialNumber(int mSerialNum) {
        this.mSerialNum = mSerialNum;
    }

    // 用于对请求的排序处理,根据优先级和加入到队列的序号进行排序
    @Override
    public int compareTo(Request<T> another) {
        Priority myPriority = this.getPriority();
        Priority anotherPriority = another.getPriority();
        // 如果优先级相等,那么按照添加到队列的序列号顺序来执行
        return myPriority.equals(another) ? this.getSerialNumber() - another.getSerialNumber()
                : myPriority.ordinal() - anotherPriority.ordinal();
    }


    /**
     * 网络请求Listener,会被执行在UI线程
     *
     * @author mrsimple
     * @param <T> 请求的response类型
     */
    public static interface RequestListener<T> {
        /**
         * 请求完成的回调
         *
         * @param response
         */
        public void onComplete(int stCode, T response, String errMsg);
    }

}
