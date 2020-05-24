package com.example.user.fragmentbacktask.view

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.TestApplication
import com.example.user.fragmentbacktask.kotlin.base.BaseActivity
import com.google.gson.Gson
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector


class DaggerActivity : BaseActivity(), HasSupportFragmentInjector {

//    @Inject
    var mGson: Gson? = null
//    @Inject
    var message: String? = null
//    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>? = null

    lateinit var tv_message: TextView

    override fun provideLayoutId(): Int {
        return R.layout.activity_dagger
    }

    override fun initCreate(savedInstanceState: Bundle?) {
//        TestApplication.getSelf().component().inject(this)
        tv_message = findViewById(R.id.tv_message)

        tv_message.text = message
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }
}
