package com.example.user.fragmentbacktask.viewmodel;

import java.lang.System;

/**
 * Created by huangyuxi on 2019-06-11
 * Description: 携带状态的LiveData
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\u000b\u001a\u00020\tJ\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\r\u001a\u00020\tJ\u0006\u0010\u000e\u001a\u00020\tJ\u0013\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0011R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/example/user/fragmentbacktask/viewmodel/StateLiveData;", "T", "Landroidx/lifecycle/MutableLiveData;", "()V", "state", "Lcom/example/user/fragmentbacktask/viewmodel/StateLiveData$State;", "getState", "()Landroidx/lifecycle/MutableLiveData;", "changeState", "", "s", "clearState", "postError", "postLoading", "postSuccess", "postValueAndSuccess", "value", "(Ljava/lang/Object;)V", "State", "app_debug"})
public final class StateLiveData<T extends java.lang.Object> extends androidx.lifecycle.MutableLiveData<T> {
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.example.user.fragmentbacktask.viewmodel.StateLiveData.State> state = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<com.example.user.fragmentbacktask.viewmodel.StateLiveData.State> getState() {
        return null;
    }
    
    public final void postValueAndSuccess(T value) {
    }
    
    public final void clearState() {
    }
    
    public final void postLoading() {
    }
    
    public final void postSuccess() {
    }
    
    public final void postError() {
    }
    
    public final void changeState(@org.jetbrains.annotations.NotNull()
    com.example.user.fragmentbacktask.viewmodel.StateLiveData.State s) {
    }
    
    public StateLiveData() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/user/fragmentbacktask/viewmodel/StateLiveData$State;", "", "(Ljava/lang/String;I)V", "Idle", "Loading", "Success", "Error", "app_debug"})
    public static enum State {
        /*public static final*/ Idle /* = new Idle() */,
        /*public static final*/ Loading /* = new Loading() */,
        /*public static final*/ Success /* = new Success() */,
        /*public static final*/ Error /* = new Error() */;
        
        State() {
        }
    }
}