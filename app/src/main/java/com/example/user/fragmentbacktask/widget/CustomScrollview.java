package com.example.user.fragmentbacktask.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * Created by huangyuxi on 2019-07-12
 */
public class CustomScrollview extends NestedScrollView {

    public CustomScrollview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 在滑动前通知父控件，如果父控件消耗了滑动距离 则返回的consumed里面的值就不为0
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }


    // 是否有嵌套的滑动父控件
    @Override
    public boolean hasNestedScrollingParent(int type) {
        return super.hasNestedScrollingParent(type);
    }

    // 告诉父控件开始滑动了，如果有父滑动控件，并且父滑动控件想要和子控件一起处理滑动的话，就会返回True
    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    // 停止嵌套滑动
    @Override
    public void stopNestedScroll(int type) {
        super.stopNestedScroll(type);
    }
}
