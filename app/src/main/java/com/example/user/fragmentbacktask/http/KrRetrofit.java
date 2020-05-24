package com.example.user.fragmentbacktask.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum KrRetrofit {
    INSTANCE;
    private final Retrofit retrofit;

    KrRetrofit() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.INSTANCE.getOkHttpClient())
                //baseUrl
                .baseUrl(ApiConstants.BASE_URL)
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                //Rx转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //创建
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
