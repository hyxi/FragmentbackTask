package com.example.user.fragmentbacktask.dagger.module;


import android.content.Context;
import android.location.LocationManager;

import com.example.user.fragmentbacktask.TestApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

@Module
public class AndroidModule {

    private TestApplication application;

    public AndroidModule(TestApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context ApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }
}
