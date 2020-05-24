package com.example.user.fragmentbacktask.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

/**
 * Created by huangyuxi on 2019-08-22
 */
public class CustomViewGroup extends RelativeLayout {

    private String TAG = "CustomViewGroup";

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG + ": dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG + ": dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG + ": dispatchTouchEvent ACTION_MOVE");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG + ": onInterceptTouchEvent ACTION_DOWN");
                return false;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG + ": onInterceptTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG + ": onInterceptTouchEvent ACTION_MOVE");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG + ": onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG + ": onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG + ": onTouchEvent ACTION_MOVE");
                return false;
        }
        return super.onTouchEvent(event);
    }


}
