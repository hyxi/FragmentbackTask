package com.example.user.fragmentbacktask.dagger;


import com.example.user.fragmentbacktask.dagger.module.MainModule;

import dagger.Component;
import dagger.android.DaggerActivity;

@Component(modules = MainModule.class)
public interface MainComponent {

//    void inject(DaggerActivity activity);
}
