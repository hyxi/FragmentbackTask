package com.example.user.fragmentbacktask.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by user on 2016/6/28.
 */
public class JellyTextView extends TextView {

    private OverScroller mScroller;

    private float lastX;
    private float lastY;

    private float startX;
    private float startY;

    public JellyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
    }

    int movex = 0;
    int movey =0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX(); //相对于屏幕
                float x = event.getX(); //空间内部计算
                Log.i("x","rawX:"+lastX+"//x:"+x);
                lastY = event.getRawY();
                if(movex<50) {
                    return true;
                }else{
                    return super.onTouchEvent(event);
                }
            case MotionEvent.ACTION_MOVE:
                float disX = event.getRawX() - lastX;
                float disY = event.getRawY() - lastY;
                offsetLeftAndRight((int) disX);
                offsetTopAndBottom((int) disY);
                movex = (int) getX();
                movey = (int) getY();
                if(movex>50){
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }
                lastX = event.getRawX();
                lastY = event.getRawY();
                Log.i("Tag","ACTION_MOVE"+"xy:"+movex+"//"+"startY"+movey);
            case MotionEvent.ACTION_UP:
//                mScroller.startScroll((int)getX(),(int)getY(),-(int)(getX() - startX),
//                -(int)(getY() - startY));
//                invalidate();
                Log.i("tag","ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){// mScroller.startScroll回调
            setX(mScroller.getCurrX());
            Log.i("scroll","x:"+mScroller.getCurrX());
            setY(mScroller.getCurrY());
            Log.i("scroll","y:"+mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = getX();
        startY = getY();
        Log.i("Tag","onsizeChanged"+"xy:"+startX+"//"+"startY"+startY);
    }

    public void springBack(){
        if (mScroller.springBack((int) getX(), (int) getY(), 0, (int) getX(), 0,
                (int) getY() - 100)) {
            Log.d("TAG", "getX()=" + getX() + "__getY()=" + getY());
            invalidate();
        }
    }
}
