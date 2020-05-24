package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;

/**
 * Created by huangyuxi on 2019-08-05
 * Title: 虚线
 */
public class DottedLineView extends View {

    private Paint mPaint;
    private int mHeight;
    private int mLineColor;
    private Path mPath;
    private float mLineStrokeHeight;
    private int mDottedLineWidth; // 虚线宽

    public DottedLineView(Context context) {
        this(context, null);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
        initStyle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = getHeight();
            height = desired;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desired, heightSize);
            }
        }
        setMeasuredDimension(widthMeasureSpec, height);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DottedLineView);
        mLineColor = array.getColor(R.styleable.DottedLineView_line_color, Color.BLACK);
        mLineStrokeHeight = array.getDimension(R.styleable.DottedLineView_line_stroke_height, StringUtils.dp2px(1));
        mDottedLineWidth = (int) array.getDimension(R.styleable.DottedLineView_dotted_line_width, 15);
        array.recycle();
    }

    private void initStyle() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineStrokeHeight);
        mPaint.setColor(mLineColor);
        PathEffect pathEffect = new DashPathEffect(new float[]{mDottedLineWidth, mDottedLineWidth * 3 / 4f}, 0);
        mPaint.setPathEffect(pathEffect);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(0, 0);
        mPath.lineTo(0, mHeight);
        canvas.drawPath(mPath, mPaint);
    }

}
