package com.example.apt_api;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

public class ProxyTool {

    public static final String SUFFIX = "$$Proxy";

    @UiThread
    public static void bind(@NonNull Activity target) {
        View sourceView = target.getWindow().getDecorView();
        createBinding(target, sourceView);
    }

    @UiThread
    public static void bind(@NonNull View target) {
        createBinding(target, target);
    }

    public static void createBinding(@NonNull Object target, @NonNull View root) {
        try {
            //生成类名+后缀名的代理类，并执行注入操作
            Class<?> targetClass = target.getClass();
            Class<?> proxyClass = Class.forName(targetClass.getName() + SUFFIX);
            IProxy proxy = (IProxy) proxyClass.newInstance();
            proxy.inject(target, root);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
