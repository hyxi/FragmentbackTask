package com.example.user.fragmentbacktask.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * Created by huangyuxi on 2019-08-22
 */
public class CustomView extends View {

    public static final String TAG = "CustomView";

    private Scroller mScroller;
    private int mLastX;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                Logger.d(TAG + ": dispatchTouchEvent ACTION_DOWN");
               break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG + ": dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG + ": dispatchTouchEvent ACTION_MOVE");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG + ": onTouchEvent ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG + ": onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG + ": onTouchEvent ACTION_MOVE");
                int deltaX = mLastX - x;
                //边界检测判断，防止滑块越界
                break;
            case MotionEvent.ACTION_CANCEL:
                Logger.d(TAG + ": onTouchEvent ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }
}
