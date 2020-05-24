package com.example.user.fragmentbacktask.activity

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.adapter.RecycleAdapter
import com.example.user.fragmentbacktask.utils.status.StatusBarUtils
import com.orhanobut.logger.Logger

class FullActivityActivity : AppCompatActivity() {

    lateinit var rvList: RecyclerView
    lateinit var mAdapter: RecycleAdapter
    lateinit var toolBar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_activity)

        rvList = findViewById(R.id.rv_list)
        toolBar = findViewById(R.id.tool_bar)

        StatusBarUtils.setTranslucentForImageView(this, null)
        toolBar.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0)

        val layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL, false)
        rvList.layoutManager = layoutManager

        initData()
    }

    private fun initData() {
        mAdapter = RecycleAdapter(this)
        var list = ArrayList<String>()
        for(i in 1.rangeTo(20)) {
            list.add("data num $i")
        }

        var uriString = "krtou://crmCompany/121?projectAlbumId=302"

        var uri = Uri.parse(uriString)
        var albumId = uri.getQueryParameter("projectAlbumId")
        Logger.d("albumId: $albumId")


        mAdapter.addList(list)
        rvList.adapter = mAdapter
        var animator = ObjectAnimator.ofInt(toolBar, "backgroundColor",
                0x00ff0000, 0x6600ff00)
        animator.duration = 3000
        animator.setEvaluator(ArgbEvaluator())
        animator.start()

        var imageView = ImageView(this)
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_success)
                .transforms(CenterCrop(), RoundedCorners(10))
var url = ""

        Glide.with(this).load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)


    }
}
