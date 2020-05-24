package com.example.user.fragmentbacktask.http;

import okhttp3.OkHttpClient;

public enum OKHttpFactory {
    INSTANCE;

    private final OkHttpClient okHttpClient;

    OKHttpFactory() {
       OkHttpClient.Builder builder = new OkHttpClient.Builder()
               .retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
