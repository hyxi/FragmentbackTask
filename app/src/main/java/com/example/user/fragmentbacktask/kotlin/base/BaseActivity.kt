package com.example.user.fragmentbacktask.kotlin.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.user.fragmentbacktask.TestApplication

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = provideLayoutId()
        if (id <= 0) {
            return
        }
        setContentView(id)
        unbinder = ButterKnife.bind(this)
        mContext = this
        initCreate(savedInstanceState)
    }

    @LayoutRes
    abstract fun provideLayoutId(): Int

    abstract fun initCreate(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
        TestApplication.getRefWatcher(this).watch(this)
    }

}
