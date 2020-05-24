package com.example.user.fragmentbacktask.kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.user.fragmentbacktask.R

/**
 * Created by huangyuxi on 2019-05-29
 * Title:
 */
class TestFragment : Fragment() {

    lateinit var rootView : View
    lateinit var tv_content: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_test, container, false)
        arguments?.let {
            val from: String? = it.getString("from")
            val type = it["type"]
            val content = from?.plus(type)
            tv_content = rootView.findViewById(R.id.tv_content)
            tv_content.text = content
        }
        return rootView
    }
}
