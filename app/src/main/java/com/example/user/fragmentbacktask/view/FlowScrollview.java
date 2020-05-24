package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.user.fragmentbacktask.adapter.PictureAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016/1/27.
 */
public class FlowScrollview extends HorizontalScrollView {

    private int mScreenWidth;
    private PictureAdapter mAdapter;
    private LinearLayout mContainer;
    //子view的宽高
    private Map<View, Integer> mViewPos = new HashMap<>();
    private int mChildHeight, mChildWidth;
    private int mFristIndex, mCurrentIndex;
    private int mCountOneScreen;
    private int mCountScreen;
    private int oldIndex;

    private int mLastMotionX, mActivePointerId;


    public FlowScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        mFristIndex = 0;
        oldIndex = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContainer = (LinearLayout) getChildAt(0);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getChildCount() == 0) {
                    return false;
                }
                mLastMotionX = (int) event.getX();
                mActivePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = event.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    Log.e("tag", "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }
                final int x = (int) event.getX(activePointerIndex);
                int deltaX = mLastMotionX - x;
                mFristIndex =mFristIndex+ deltaX/(mChildWidth/2);
                if(mFristIndex <0 ){
                    break;
                }
                if (Math.abs(deltaX) > mChildWidth/2 && mFristIndex != oldIndex) {
                    if(mFristIndex >oldIndex) {
                        showNext();
                    }else if(mFristIndex < oldIndex){
                        showPro();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }


        return super.onTouchEvent(event);
    }

    public void setAdapter(PictureAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) getChildAt(0);
        View view = mAdapter.getView(0, null, mContainer);
        mContainer.addView(view);
        if (mChildWidth == 0 && mChildHeight == 0) {
            //强制计算view的宽高
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);

            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            mCountOneScreen = mScreenWidth / mChildWidth + 1;
        }

        initFirstScreenChildren(mCountOneScreen);
    }

    private void showNext() {
        if (mCurrentIndex == mAdapter.getCount() - 1) {
            return;
        }
        if (mCountScreen == mCountOneScreen * 2 + 1) {
            mViewPos.remove(mContainer.getChildAt(0));
            mContainer.removeViewAt(0);
            mCountScreen--;
        }
        View view = mAdapter.getView(++mCurrentIndex, null, mContainer);
        mContainer.addView(view);
        mViewPos.put(view, mCurrentIndex);
        oldIndex++;
        mCountScreen++;

    }

    private void showPro() {
        if (mFristIndex == 0)
            return;
        int index = mFristIndex - 1;
        int oldViewPos = mContainer.getChildCount() - 1;
        if (mCountScreen == mCountOneScreen * 2 + 1) {
            mViewPos.remove(mContainer.getChildAt(oldViewPos));
            mContainer.removeViewAt(oldViewPos);
            mCountScreen--;
        }
        View view = mAdapter.getView(index, null, mContainer);
        mViewPos.put(view, index);
        mContainer.addView(view, 0);
        scrollTo(mChildWidth, 0);
        mCurrentIndex--;
        oldIndex--;
        mCountScreen++;
    }

    private void initFirstScreenChildren(int mCountOneScreen) {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();
        mCountScreen = mCountOneScreen;
        for (int i = 0; i < mCountOneScreen; i++) {
            View view = mAdapter.getView(i, null, mContainer);
            mContainer.addView(view);
            mViewPos.put(view, i);
            mCurrentIndex = i;
        }
    }

}
