package com.example.user.fragmentbacktask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.StringBuilder

/**
 * Created by huangyuxi on 2019-06-11
 */
class ScoreViewModel : ViewModel() {

    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var data1 = Transformations.switchMap(currentName) {
        val temp: MutableLiveData<String> = MutableLiveData()
        Thread(Runnable {
            Thread.sleep(2000)
            temp.postValue(StringBuilder(it)
                    .append("aaaa")
                    .toString())
        }).start()
        temp
    }

    // 清除引用 viewmodel 销毁
    override fun onCleared() {
        super.onCleared()
    }

}
