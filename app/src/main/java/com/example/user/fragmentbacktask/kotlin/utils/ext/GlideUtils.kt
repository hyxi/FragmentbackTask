package com.example.user.fragmentbacktask.kotlin.utils.ext

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.kotlin.utils.DisplayUtil

fun ImageView.load(url: String?) {
    val requestOptions = RequestOptions().placeholder(R.drawable.hugh).error(R.drawable.hugh)
    get(url).apply(requestOptions)
            .into(this)
}

fun ImageView.loadRound(url: String?, centerCrop: Boolean = false) {
    val requestOptions = RequestOptions()
            .placeholder(R.drawable.hugh)
//            .transform(RoundedCornersTransformation(this.context, DisplayUtil.dp2px(10f), 0,
//            centerCrop = centerCrop))
    get(url).apply(requestOptions)
            .into(this)
}

fun ImageView.get(url: String?):RequestBuilder<Drawable> = Glide.with(this.context).load(url)