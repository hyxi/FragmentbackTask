package com.example.user.fragmentbacktask.kotlin.view

import android.os.Bundle
import android.widget.ImageView
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.activity.BaseActivity
import com.example.user.fragmentbacktask.kotlin.fragment.TestFragment
import com.example.user.fragmentbacktask.kotlin.utils.click
import com.example.user.fragmentbacktask.kotlin.utils.ext.newInstance

class NewTestActivity : BaseActivity() {

    lateinit var ivBack: ImageView

    override fun provideLayoutId(): Int {
        return R.layout.activity_new_test
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        ivBack = findViewById(R.id.iv_back)
        ivBack.click {
            finish()
        }

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = newInstance<TestFragment>(Pair("from", "newPage"), Pair("type", 1))
        transaction.add(R.id.fl_container, fragment)
        transaction.commit()
    }
}
