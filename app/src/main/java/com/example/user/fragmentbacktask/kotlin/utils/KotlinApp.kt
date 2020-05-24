package com.example.user.fragmentbacktask.kotlin.utils

import android.app.Application
import kotlin.properties.Delegates

// 委托属性
class KotlinApp : Application() {
    companion object {
        var instance: KotlinApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

