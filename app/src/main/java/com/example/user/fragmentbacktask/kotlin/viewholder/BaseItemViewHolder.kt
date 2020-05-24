package com.example.user.fragmentbacktask.kotlin.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class BaseItemViewHolder : RecyclerView.ViewHolder {

    constructor(itemView: View) : super(itemView) {}

    constructor(context: Context, @LayoutRes layoutId: Int, parent: ViewGroup) : super(LayoutInflater.from(context).inflate(layoutId, parent, false)) {}
}
