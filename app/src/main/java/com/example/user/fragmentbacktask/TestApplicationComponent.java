package com.example.user.fragmentbacktask;

import com.example.user.fragmentbacktask.dagger.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by huangyuxi on 2018/12/28
 * Title:
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        MainModule.class
})
public interface TestApplicationComponent extends AndroidInjector<TestApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TestApplication>{}
}
