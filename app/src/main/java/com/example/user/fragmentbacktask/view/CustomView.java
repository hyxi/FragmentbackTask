package com.example.user.fragmentbacktask.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.example.user.fragmentbacktask.activity.CustomViewActivity;

/**
 * Created by user on 2016/8/16.
 */
public class CustomView extends View {

    private Context mContext;
    GestureDetector mDetector = new GestureDetector(mContext,new MListener());

    private OverScroller mScroller;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new OverScroller(context);
        //硬件加速
        if(!isInEditMode()){
            setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }
        //通过这样的修改以后，PieChart.PieView.onDraw()只会在第一次现实的时候被调用。之后，
        // pie chart会被缓存为一张图片，并通过GPU来进行重画不同的角度。
    }


    class MListener extends GestureDetector.SimpleOnGestureListener{
        //如果你再onDown()里面返回false，系统会认为你想要忽略后续的gesture,
        // 那么GestureDetector.OnGestureListener的其他回调方法就不会被执行到了
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        //尽管速率是通过GestureDetector来计算的，许多开发者感觉使用这个值使得fling动画太快。
        // 通常把x与y设置为4到8倍的关系
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            mScroller.fling(currentX, currentY, velocityX / 4, velocityY / 4, minX, minY, maxX, maxY);
            postInvalidate();
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Touch事件本身并不是特别有用。如今的touch UI定义了touch事件之间的相互作用，叫做gentures。
        boolean result = mDetector.onTouchEvent(event);
        if(!result){
            if(event.getAction() == MotionEvent.ACTION_UP){
                stopScrolling();
                result = true;
            }
        }
        return result;
    }


    private void stopScrolling(){

        ValueAnimator animator = ValueAnimator.ofInt(0,1);
        animator.setObjectValues("rotate");
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatcVal = (float) animation.getAnimatedValue();
                //同时改变多个属性值
                setAlpha(floatcVal);
                setScaleX(floatcVal);
                setScaleY(floatcVal);
            }
        });

    }

}
