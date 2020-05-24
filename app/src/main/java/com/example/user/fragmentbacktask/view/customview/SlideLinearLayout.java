package com.example.user.fragmentbacktask.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import java.util.HashMap;

/**
 * haungyx
 */

public class SlideLinearLayout extends LinearLayout {

    private static final String TAG = "SlideLayout";

    private int mTouchSlop;
    private boolean mDragging;
    private float mLastY;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;//可滑动
    private View header;
    private int topHeight;

    private int screenWidth, screenHeight;
    //1表示 显示头部  2表示显示底部
    int state = 0;
    View center, bottom;


    int height = 0;//总高度


    private int mMaximumVelocity, mMinimumVelocity;

    public SlideLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;//减去下边的高度
    }

    @Override
    protected void onFinishInflate() {
        header = getChildAt(0);
        center = getChildAt(1);
        bottom = getChildAt(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");
        // 遍历所有子视图，进行measure操作
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != View.GONE) {
                // 测量子控件
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                height += child.getMeasuredHeight();
                // 支持子视图设置的android:layout_margin属性
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        topHeight = header.getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "dispatchTouchEvent ACTION_MOVE");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent ACTION_MOVE");
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                mLastY = y;
                return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private int headDis = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);

        HashMap<String, String> map = new HashMap<>();
        map.put("ss", "");


        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent ACTION_DOWN");
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent ACTION_MOVE");
                float dy = y - mLastY;
                Log.i("dis", dy + "");
                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
                    if (state == 1) {
                        headDis += dy;
                        Log.i("dis", "dd://" + headDis + "");
                        scrollBy(0, -(int) (dy * 0.3));//0.3 为阻尼系数
                    } else {
                        scrollBy(0, -(int) dy);
                    }
                    headDis += dy;

                    //header.layout(0, headDis, header.getWidth(), header.getHeight() + headDis);
                    //this.layout(0, headDis, getWidth(), getHeight());
                } else if (getScrollY() + screenHeight > height + 100) {
                    float my = height + 100 - screenHeight;
                    scrollBy(0, -(int) my);
                }
                // 边界值检查
                int scrollY = getScrollY();
                Log.i(TAG, scrollY + "//" + height);
//                // 已经到达底部，上拉多少，就往下滚动多少
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent ACTION_UP");

                /**回弹效果实现 使用动画实现
                 *  int curY = getScrollY();
                 TranslateAnimation animation = new TranslateAnimation(0, 0, -curY, 0);
                 animation.setDuration(300);
                 animation.setInterpolator(new AccelerateInterpolator((float) 4.0));
                 this.startAnimation(animation);
                 scrollTo(0, 0)
                 */
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityNY = (int) mVelocityTracker.getYVelocity();
                Log.i(TAG, "velocityNY://" + velocityNY + "");//-40 100
//                    if (Math.abs(velocityNY) > mMinimumVelocity)
//                        fling(velocityNY, height - screenHeight);
//                    else{
//                        fling(mMinimumVelocity, height - screenHeight);
//                    }
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY, height-screenHeight);
                }
//                    fling(0,height -screenHeight);
//                    int curY = getScrollY();
//                    TranslateAnimation animation = new TranslateAnimation(0,0,curY,0);
//                    animation.setDuration(300);
//                    this.setAnimation(animation);

//                    layout(0,height -screenHeight,0,0);
                state = 0;
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void fling(int velocityY, int y) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, y);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            state = 1;
        } else if (getScrollY() + screenHeight > height) {//滑动到底
            state = 2;
        }

        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll");
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            //view一旦重绘就会回调onDraw，onDraw中又会调用computeScroll，不停绘制
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
