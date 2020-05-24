package com.example.user.fragmentbacktask.viewmodel

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by huangyuxi on 2019-08-20
 */
class  AdapterDiffCallBack<T>(var oldList:List<T>, var newList: List<T>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition]?.javaClass == newList[newItemPosition]?.javaClass
        return false
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    // 数据具体哪里不一致
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldBean = oldList[oldItemPosition]
        val newBean = newList[newItemPosition]
        val bundle = Bundle()
        if (oldBean != newBean) {
            bundle.putString("content", "a")
        }
        if (bundle.size() > 0) {
            return bundle
        }
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

//    fun onBindViewHolder(ViewHolder holder, position:Int , payload:List<Bundle>) {
//        if (payload.isEmpty()) {
//           super.onBindViewHolder(holder, position, payload)
//        } else {
//            val bundle = payload.get(0)
//            for(key: String in bundle.keySet()) {
//                if (key == "content") {
//                    holder.contentView.setText(bundle.getString("content"))
//                }
//            }
//        }
//    }
}