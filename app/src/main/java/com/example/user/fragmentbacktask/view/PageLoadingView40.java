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
 * 页面加载动画类
 * 
 * @author
 * 
 */
public class PageLoadingView40 extends FrameLayout {

	private static final int SOUFUN_LOADING_BAR = R.mipmap.page_loading_bar_40x40;
	private static final int SOUFUN_LOADING = R.mipmap.page_loading_40x40;
	
	private Animation animation;
	private Rotate3d rotate3d;

	private ImageView iv_loading_bar;
	private ImageView iv_loading;

	public PageLoadingView40(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PageLoadingView40(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PageLoadingView40(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		iv_loading_bar = new ImageView(context);
		iv_loading_bar.setImageResource(SOUFUN_LOADING_BAR);
		iv_loading_bar.setLayoutParams(params);
		iv_loading = new ImageView(context);
		iv_loading.setImageResource(SOUFUN_LOADING);
		iv_loading.setLayoutParams(params);
		this.addView(iv_loading_bar);
		this.addView(iv_loading);
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);
		if(visibility == View.VISIBLE) {
			animation = createRotateAnimation();
			iv_loading_bar.startAnimation(animation);
			post(new Runnable() {
				@Override
				public void run() {
					rotate3d = createRotate3dAnimation();
					iv_loading.startAnimation(rotate3d);
				}
			});
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

	private Rotate3d createRotate3dAnimation() {
		final float centerX = this.getWidth() / 2.0f;
		final Rotate3d rotation = new Rotate3d(0, 360, centerX, 0, 0, true);
		rotation.setDuration(1000);
		rotation.setRepeatCount(-1);
		rotation.setInterpolator(new LinearInterpolator());
		return rotation;
	}

	/**
	 * 开始动画
	 */
	public void startAnimation() {
		if (animation != null)
			iv_loading_bar.startAnimation(animation);
		if (rotate3d != null)
			iv_loading.startAnimation(rotate3d);
	}

	/**
	 * 停止动画
	 */
	public void stopAnimation() {
		iv_loading_bar.clearAnimation();
		iv_loading.clearAnimation();
		animation = null;
		rotate3d = null;
	}
}
