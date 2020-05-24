package com.example.user.fragmentbacktask.dagger.module;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public String provideMessage() {
        return "{\"content:\": \"这是一条测试消息\"}";
    }


}
