package com.example.user.fragmentbacktask.view.seekbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import androidx.core.content.ContextCompat;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;

/**
 * Created by huangyuxi on 2019-06-13
 */
public class SeekBar {
    private float currentPercent;
    private int range;
    private Bitmap bitmap;
    private int left, top, right, bottom;
    private int bitmapWidth;
    private RangeSeekBar parentView;

    private Runnable refreshRunnable;
    private static final int PROGRESS_ANIM_DURATION = 80;
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();

    private ValueAnimator valueAnimator;

    public SeekBar(Context context, RangeSeekBar view) {
        parentView = view;
        bitmapWidth = StringUtils.dp2px(30);
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.time_seek_bar);
        if (drawable != null) {
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapWidth, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, bitmapWidth, bitmapWidth);
            drawable.draw(canvas);
        }
    }

    public int getBarHeight() {
        return bitmapWidth;
    }

    private void refreshProgress() {
        if (refreshRunnable == null) {
            refreshRunnable = new RefreshProgressRunnable();
        }
        parentView.post(refreshRunnable);
    }

    // 更新bar 背景色
    private class RefreshProgressRunnable implements Runnable {
        @Override
        public void run() {
            parentView.computeProgressPath();
        }
    }

    private synchronized void doRefreshProgress(int id, int progress, boolean fromUser,
                                                boolean callBackToApp, boolean animate) {
        final float scale = currentPercent;

        if (animate) {
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            valueAnimator = ValueAnimator.ofFloat();
            valueAnimator.setDuration(PROGRESS_ANIM_DURATION);
            valueAnimator.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            valueAnimator.start();
        }
    }

    void onSizeChanged(int centerX, int centerY, int length) {
        left = StringUtils.dp2px(10);
        top = centerY;
        bottom = centerY + bitmapWidth;
        right = centerX + bitmapWidth;
        range = length;
    }

    public boolean collide(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int offset = (int) (range * currentPercent);
        int topBoundary = top - StringUtils.dp2px(4);
        int bottomBoundary = bottom + StringUtils.dp2px(4);
        return x > left + offset && x < right + offset && y > topBoundary && y < bottomBoundary;
    }

    public void slide(float percent) {
        if (percent < 0) percent = 0;
        else if (percent > 1) percent = 1;
        currentPercent = percent;
        refreshProgress();
    }

    float getProgressPercent() {
        return currentPercent;
    }

    void draw(Canvas canvas) {
        int offset = (int) (currentPercent * range);
        canvas.save();
        canvas.translate(offset, 0);
        canvas.drawBitmap(bitmap, left, top, null);
        canvas.restore();
    }
}
