package com.example.apt_api;

import android.view.View;

/**
 * Created by huangyuxi on 2019/4/10
 * Title:
 */
public interface IProxy<T> {
    /**
     * @param target 所在的类
     * @param root 查找 View 的地方
     */
    public void inject(final T target, View root);
}
