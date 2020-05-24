package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.user.fragmentbacktask.R;

/**
 * Created by burke
 * 页面加载动画
 */
public class PageLoadingView extends FrameLayout {

    private static final int SOUFUN_LOADING_BAR = R.mipmap.loading;
    private ImageView iv_loading_bar;
    private Animation animation;

    public PageLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PageLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PageLoadingView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iv_loading_bar = new ImageView(context);
        iv_loading_bar.setImageResource(SOUFUN_LOADING_BAR);
        iv_loading_bar.setLayoutParams(params);
        this.addView(iv_loading_bar);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility == View.VISIBLE) {
            animation = createRotateAnimation();
            iv_loading_bar.startAnimation(animation);
        }
    }

    private Animation createRotateAnimation() {
        Animation animation = new RotateAnimation(0, +360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(900);
        animation.setRepeatCount(-1);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (animation != null)
            iv_loading_bar.startAnimation(animation);
    }

    /**
     * 停止动画
     */
    public void stopAnimation() {
        iv_loading_bar.clearAnimation();
        animation = null;
    }

}
