package com.example.user.fragmentbacktask.kotlin.viewholder

import android.content.Context
import android.view.ViewGroup
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder
import com.example.user.fragmentbacktask.kotlin.model.FindModel

class ItemProjectViewHolder(context: Context, parent: ViewGroup):
        BaseViewHolder<FindModel>(context, R.layout.item_project_view_holder, parent){



    override fun bind(data: FindModel?) {
        data?.run {
        }
    }


}
