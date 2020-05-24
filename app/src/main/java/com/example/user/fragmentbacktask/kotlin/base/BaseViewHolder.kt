package com.example.user.fragmentbacktask.kotlin.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife

abstract class BaseViewHolder<T> : RecyclerView.ViewHolder{

    lateinit var mContext: Context

    constructor(itemView: View): super(itemView) {
        ButterKnife.bind(this, itemView)
    }

    constructor(context: Context, itemView: View): super(itemView) {
        mContext = context
        ButterKnife.bind(this, itemView)
    }

    constructor(context: Context, @LayoutRes layoutId: Int, parent: ViewGroup):
            super(LayoutInflater.from(context).inflate(layoutId, parent, false)) {
        ButterKnife.bind(this, itemView)
        mContext = context
    }

    abstract fun bind(data: T?)

}