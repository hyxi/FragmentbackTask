package com.example.user.fragmentbacktask.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.user.fragmentbacktask.R

/**
 * Created by huangyuxi on 2019-06-11
 */
class TwoFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    lateinit var tvContent: TextView
    lateinit var btnChange: Button
    lateinit var baseActivity: FragmentActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity == null) {
            return
        }
        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)

        tvContent.text = viewModel.data.value ?: "two 没有内容"

        viewModel.data.observe(this, Observer {
            tvContent.text = "one $it"
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_two_layout, container, false)
        tvContent = rootView.findViewById(R.id.tv_content)
        btnChange = rootView.findViewById(R.id.btn_change)


        btnChange.setOnClickListener {
            viewModel.data.changeValue("ss")
        }

        return rootView
    }

}
