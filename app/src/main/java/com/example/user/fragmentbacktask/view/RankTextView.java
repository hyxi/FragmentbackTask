package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;

/**
 * Created by huangyuxi on 2019-06-04
 * 排行view label
 */
public class RankTextView extends View {
    private String value;
    private Paint textPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    private RectF rectF = new RectF();

    private int offsetX;

    private int corner;
    private static int DEFAULT_WIDTH = StringUtils.dp2px(30);
    private static int DEFAULT_HEIGHT = StringUtils.dp2px(20);

    public RankTextView(Context context) {
        this(context, null);
    }

    public RankTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RankTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);

        backgroundPaint.setAntiAlias(true);
        rectF.left = 0;
        rectF.top = 0;
        corner = StringUtils.dp2px(2);
    }

    public void setValue(int value) {
        float size = StringUtils.dp2px(12);
        this.value = String.valueOf(value);
        if (value < 4) {
            textPaint.setTextSize(size);
            backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_6871DC));
            textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        } else if (value <= 6) {
            textPaint.setTextSize(size);
            backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_6871DC_50));
            textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white_60));
        } else {
            offsetX = 2;
            textPaint.setTextSize(StringUtils.dp2px(14));
            textPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_8A97A6));
            backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.transparent));
        }
        if (value / 10 == 0) {
            offsetX = 4;
            rectF.right = StringUtils.dp2px(15);
            rectF.bottom = StringUtils.dp2px(15);
        } else {
            offsetX = 2;
            Rect bound = new Rect();
            textPaint.getTextBounds(this.value, 0, this.value.length(), bound);
            rectF.right = bound.width() + StringUtils.dp2px(10);
            rectF.bottom = bound.height() + StringUtils.dp2px(6);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalWidth = getMeasuredLength(widthMeasureSpec, true);
        int finalHeight = getMeasuredLength(heightMeasureSpec, false);
        setMeasuredDimension(finalWidth, finalHeight);
    }

    private int getMeasuredLength(int length, boolean isWidth) {
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        int size;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (specMode == MeasureSpec.AT_MOST) {
            if (isWidth) {
                int mWidth = !TextUtils.isEmpty(value) ? (int) rectF.width() : DEFAULT_WIDTH + padding;
                size = Math.min(mWidth, specSize);
            } else {
                int mHeight = !TextUtils.isEmpty(value) ? (int) rectF.height() : DEFAULT_HEIGHT + padding;
                size = Math.min(mHeight, specSize);
            }
        } else {
            size = specSize;
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(value)) {
            canvas.drawRoundRect(rectF, corner, corner, backgroundPaint);
            canvas.drawText(value, StringUtils.dp2px(offsetX), rectF.height() - StringUtils.dp2px(3), textPaint);
        }
    }
}
