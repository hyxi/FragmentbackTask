package com.example.user.fragmentbacktask.utils.asyncinflate;

import android.view.View;
import android.view.ViewGroup;

import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

/**
 * Created by huangyuxi on 2019-09-12
 */
public class AsyncInflateItem {
    private String inflateKey;
    private int layoutResId;
    private ViewGroup parent;
    private AsyncLayoutInflater.OnInflateFinishedListener callBack;
    private View inflatedView;
    private boolean cancelled;
    private boolean inflating;

    public AsyncInflateItem(String inflateKey, int layoutResId) {
        this.inflateKey = inflateKey;
        this.layoutResId = layoutResId;
    }

    public String getInflateKey() {
        return inflateKey;
    }

    public void setInflateKey(String inflateKey) {
        this.inflateKey = inflateKey;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    public void setLayoutResId(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public ViewGroup getParent() {
        return parent;
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

    public AsyncLayoutInflater.OnInflateFinishedListener getCallBack() {
        return callBack;
    }

    public void setCallBack(AsyncLayoutInflater.OnInflateFinishedListener callBack) {
        this.callBack = callBack;
    }

    public View getInfaltedView() {
        return inflatedView;
    }

    public void setInfaltedView(View inflatedView) {
        this.inflatedView = inflatedView;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isInflating() {
        return inflating;
    }

    public void setInflating(boolean inflating) {
        this.inflating = inflating;
    }
}
