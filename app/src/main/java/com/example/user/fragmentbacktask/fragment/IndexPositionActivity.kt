package com.example.user.fragmentbacktask.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.kotlin.base.BaseActivity
import com.example.user.fragmentbacktask.kotlin.base.BaseViewHolder

class IndexPositionActivity : BaseActivity() {

    lateinit var rvList: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    override fun provideLayoutId(): Int {
        return R.layout.activity_index_position
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        rvList = findViewById(R.id.rv_list)

        mLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvList.layoutManager = mLayoutManager
        var lastPosition = 0
        var offsetY: Float? = 0f
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            lastPosition = mLayoutManager.findFirstVisibleItemPosition()
            offsetY = mLayoutManager.findViewByPosition(lastPosition)?.y
            mLayoutManager = object : LinearLayoutManager(mContext, RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            rvList.layoutManager = mLayoutManager
            mLayoutManager.scrollToPositionWithOffset(lastPosition, offsetY!!.toInt())
        }

        findViewById<Button>(R.id.btn_enable).setOnClickListener {
            mLayoutManager = object : LinearLayoutManager(mContext, RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }
            rvList.layoutManager = mLayoutManager
            mLayoutManager.scrollToPositionWithOffset(lastPosition, offsetY!!.toInt())
        }


        val mAdapter = PositionAdapter(mContext)
        rvList.adapter = mAdapter

        val dataList = ArrayList<PositionData>()
        for (i in 0.until(100)) {
            val itemData = PositionData("content:" + i)
            dataList.add(itemData)
        }
        mAdapter.addData(dataList)
    }


    class PositionAdapter : RecyclerView.Adapter<BaseViewHolder<PositionData>> {

        var list = ArrayList<PositionData>()
        var mContext: Context

        constructor(context: Context) : super() {
            mContext = context
        }

        fun addData(datas: List<PositionData>) {
            list.clear()
            list.addAll(datas)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PositionData> {
            return IndexViewHolder(mContext, parent)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: BaseViewHolder<PositionData>, position: Int) {
            val data = list[position]
//            if (data.index <= 0) {
//
//            }
            data.index = position
            holder.bind(data)
        }

        class IndexViewHolder : BaseViewHolder<PositionData> {
            private val textView: TextView

            constructor(context: Context, parent: ViewGroup) : super(context, R.layout.recycler_test_layout, parent) {
                textView = itemView.findViewById(R.id.textview)
            }

            override fun bind(data: PositionData?) {
                data?.let {
                    textView.text = "index: ${data.index} content:${data.content}"
                }
            }
        }
    }

}
