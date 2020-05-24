package com.example.user.fragmentbacktask.kotlin.adapter

import android.content.Context
import android.view.ViewGroup
import com.example.user.fragmentbacktask.kotlin.base.BaseAdapter
import com.example.user.fragmentbacktask.kotlin.model.FindModel
import com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder
import com.example.user.fragmentbacktask.kotlin.viewholder.ItemProjectViewHolder

class ProjectAdapter(context: Context) : BaseAdapter<FindModel>(context) {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FindModel> {
        return ItemProjectViewHolder(mContext, parent)
    }

    override fun bindItemData(holder: BaseViewHolder<FindModel>, position: Int) {
       mOnClickListener?.let {
           holder.itemView.setOnClickListener(it)
       }
        val data = mList[position]
        holder.bind(data)
    }
}
