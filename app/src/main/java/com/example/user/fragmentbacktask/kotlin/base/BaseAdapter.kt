package com.example.user.fragmentbacktask.kotlin.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

abstract class BaseAdapter<T>(var mContext: Context) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var mList: MutableList<T> = ArrayList()

    var mOnClickListener: View.OnClickListener? = null

    fun setList(list: List<T>, initList: Boolean?) {
        if (initList!!) {
            mList.clear()
        }
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return onCreateItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        bindItemData(holder, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    internal abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    internal abstract fun bindItemData(holder: BaseViewHolder<T>, position: Int)
}
