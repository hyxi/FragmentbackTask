package com.example.user.fragmentbacktask.viewmodel

import androidx.lifecycle.MutableLiveData

/**
 * Created by huangyuxi on 2019-06-11
 * Description: 携带状态的LiveData
 */
class StateLiveData<T> : MutableLiveData<T>() {
    val state = MutableLiveData<State>()

    enum class State{
        Idle, Loading, Success,Error
    }

    init {
        clearState()
    }

    fun postValueAndSuccess(value: T) {
        super.postValue(value)
        postSuccess()
    }

    fun clearState() {
        state.postValue(State.Idle)
    }

    fun postLoading() {
        state.postValue(State.Loading)
    }

    fun postSuccess() {
        state.postValue(State.Success)
    }

    fun postError() {
        state.postValue(State.Error)
    }

    fun changeState(s: State) {
        state.postValue(s)
    }
}