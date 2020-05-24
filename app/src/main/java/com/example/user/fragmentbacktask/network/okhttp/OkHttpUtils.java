package com.example.user.fragmentbacktask.network.okhttp;

import com.example.user.fragmentbacktask.network.okhttp.builder.GetBuilder;
import com.example.user.fragmentbacktask.network.okhttp.callback.Callback;
import com.example.user.fragmentbacktask.network.okhttp.request.RequestCall;
import com.example.user.fragmentbacktask.network.okhttp.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkHttpUtils {

    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private volatile static OkHttpUtils mInstance;

    public static final long DEFAULT_CONN_MILLISECONDS = 15000L;
    public static final long DEFAULT_RW_MILLISECONDS = 20000L;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
    }


    private static synchronized OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            mInstance = new OkHttpUtils(okHttpClient);
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }
    public static GetBuilder get() {
        return new GetBuilder();
    }

    public void execute(RequestCall requestCall, Callback callback){
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        return;
                    }
                     //缓存处理
//                    long cacheTime = getCacheFaileTime(pairs, type);
//                    String hashcodeUrl = getHashcodeUrl(pairs, type);
//                    qr = (T) getCacheObject(hashcodeUrl, cacheTime);
//                    if(){
//
//                    }
                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response != null && response.body() != null) {
                        response.body().close();
                    }
                }

            }
        });
    }

    private void sendFailResultCallback(final Call call,
                                       final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


}
