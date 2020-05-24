package com.example.user.fragmentbacktask.dagger;


import android.app.Application;

import com.example.user.fragmentbacktask.TestApplication;
import com.example.user.fragmentbacktask.dagger.module.AndroidModule;
import com.example.user.fragmentbacktask.dagger.module.MainModule;
import com.example.user.fragmentbacktask.kotlin.base.BaseActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AndroidModule.class,
        MainModule.class
})
public interface ApplicationComponent {

//    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(TestApplication application);

    void inject(BaseActivity activity);
}
