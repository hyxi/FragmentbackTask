package com.example.user.fragmentbacktask.view.bottompickerdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;


import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.WheelAdapter;

import java.util.LinkedList;

/**
 * 滚轮
 *
 * @author wangzengyang@gmail.com
 * @since 2013-12-26
 */
public class WheelView extends ScrollableView implements OnClickListener {
    private float lineSplitHeight;
    private Paint textPaintFirst;
    private Paint textPaintSecond;
    private Paint textPaintThird;
    private Paint linePaint;
    private float textBaseY;
    private float secondRectTop;
    private float secondRectBottom;
    private float thirdRectTop;
    private float thirdRectBottom;
    private float textSize;
    private float itemHeight;
    private int textGravity;
    private WheelAdapter adapter;

    private float contentHeight;

    private float lastEventY;

    private float scrollY;


    private float factor = 1.2f;// 选中的字增大的倍数
    private float exH = 10;// 选中的部分线的高度增量

    private VelocityTracker velocityTracker;
    private Rect firstRect, secondRect, thirdRect;

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.wheel);
        int textColorFirst = a.getColor(R.styleable.wheel_textColorFirst, -1);
        int textColorSecond = a.getColor(R.styleable.wheel_textColorSecond, -1);
        int textColorThird = a.getColor(R.styleable.wheel_textColorThird, -1);
        this.textSize = a.getDimension(R.styleable.wheel_textWheelSize, -1);
        textSize = WindowUtil.sp2px(getContext(), textSize);
        int lineColor = a.getColor(R.styleable.wheel_splitLineColor, -1);
        this.lineSplitHeight = a.getDimension(
                R.styleable.wheel_lineSplitHeight, -1);
        lineSplitHeight = WindowUtil.sp2px(getContext(), lineSplitHeight);
        this.itemHeight = this.textSize + this.lineSplitHeight;
        this.textGravity = a.getInt(R.styleable.wheel_textGravity, -1);
        a.recycle();

        this.textPaintFirst = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        this.textPaintFirst.setTextSize(textSize);
        this.textPaintFirst.setColor(textColorFirst);
        this.textPaintSecond = new TextPaint(textPaintFirst);
        this.textPaintThird = new TextPaint(textPaintFirst);
        this.textPaintSecond.setColor(textColorSecond);
        this.textPaintThird.setColor(textColorThird);

        this.linePaint = new Paint();
        this.linePaint.setColor(lineColor);
    }


    protected void init() {
        setInterpolator(getContext(), new DecelerateInterpolator());
        setOnClickListener(this);
        velocityTracker = VelocityTracker.obtain();
        WindowUtil.resize(this);
    }

    public void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        this.render();
        this.adapter.setWheelView(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.adapter == null || this.adapter.getCount() <= 0) {
            return;
        }

        canvas.save();
        canvas.clipRect(firstRect);
        drawText(canvas, this.textPaintFirst);
        canvas.restore();

        drawSplitLine(canvas);
        // 画第二部分

        canvas.save();
        canvas.clipRect(secondRect);
        drawText(canvas, this.textPaintSecond);
        canvas.restore();

        // 画第三部分
        canvas.save();
        canvas.clipRect(thirdRect);
        drawText(canvas, this.textPaintThird);
        canvas.restore();
    }

    /**
     * 画分割线
     *
     * @param canvas
     */
    private void drawSplitLine(Canvas canvas) {
        canvas.drawLine(0, this.thirdRectTop - exH / 2, getMeasuredWidth(), this.thirdRectTop - exH / 2, linePaint);
        canvas.drawLine(0, this.thirdRectTop + this.textSize
                + this.lineSplitHeight / 2 + exH / 2, getMeasuredWidth(), this.thirdRectTop + this.textSize
                + this.lineSplitHeight / 2 + exH / 2, linePaint);
    }

    /**
     * 画文本
     *
     * @param canvas
     * @param paint
     */
    private void drawText(Canvas canvas, Paint paint) {
        float maxWidth = this.adapter.getMaxWidth(paint);// 所有元素最长的一个
        int count = this.adapter.getCount();
        int px = getMeasuredWidth();
        float textWidth;
        float x;
        float y;
        String text;
        Paint paint2;
        for (int index = 0; index < count; index++) {
            if (index == getCurrentItemIndex()) {
                paint2 = new Paint(paint);
                paint2.setTextSize(textSize * factor);

            } else {
                paint2 = paint;
            }
            text = this.adapter.getItem(index);
            textWidth = paint2.measureText(text);
            x = computeTextX(maxWidth, px, textWidth);// 计算当前坐标
            y = this.textBaseY + scrollY + (index == getCurrentItemIndex() ? exH / 2 : 0);
            canvas.drawText(text, x, y, paint2);
            canvas.translate(0, itemHeight + (index == getCurrentItemIndex() ? exH : 0));
        }
    }

    private float computeTextX(float maxWidth, int px, float textWidth) {
        return (px + getPaddingLeft() - getPaddingRight()) / 2 - textWidth / 2;
    }

    public void computeTextBaseY() {
        if (this.adapter == null || this.adapter.getCount() <= 0)
            return;
        int count = this.adapter.getCount();
        this.contentHeight = count * this.textSize + (count - 1)
                * this.lineSplitHeight;
        this.textBaseY = -textPaintFirst.getFontMetrics().top
                + this.getMeasuredHeight() / 2 - this.textSize / 2;
    }

    private void computeCenterRect() {
        if (this.adapter == null || this.adapter.getCount() <= 0)
            return;
        this.thirdRectTop = getMeasuredHeight() / 2 - this.textSize / 2;
        this.thirdRectBottom = this.thirdRectTop + this.textSize
                + this.lineSplitHeight / 2 + exH / 2;
        this.secondRectTop = this.thirdRectTop - this.itemHeight;
        this.secondRectBottom = getHeight();

        firstRect = new Rect(0, 0, getMeasuredWidth(), (int) (this.thirdRectTop - exH / 2));
        secondRect = new Rect(0, (int) (this.thirdRectTop + this.textSize
                + this.lineSplitHeight / 2 + exH / 2), getMeasuredWidth(), getMeasuredHeight());
        thirdRect = new Rect(0, (int) (this.thirdRectTop - exH / 2), getMeasuredWidth(), (int) (this.thirdRectTop + this.textSize
                + this.lineSplitHeight / 2 + exH / 2));

        Log.i("info", "wheel的尺寸是:" + secondRectTop + "," + secondRectBottom + "," + thirdRectTop + "," + thirdRectBottom + "," + getHeight() + "," + getWidth());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        computeTextBaseY();
        computeCenterRect();
    }


    @Override
    public void onClick(View v) {
        LinkedList<Step> stepList = new LinkedList<Step>();
        Step step1 = Step.createDistanceStep(200, 200, 2000);
        Step step2 = Step.createDistanceStep(-200, -200);
        Step step3 = Step.createDistanceStep(200, -200);
        Step step4 = Step.createDistanceStep(0, 0);
        stepList.add(step1);
        stepList.add(step2);
        stepList.add(step3);
        stepList.add(step4);
        move(stepList);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        velocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                stopScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (event.getY() - lastEventY);
                scrollY += dy;
                makeSureScrollVisible();
                render();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                fling();
                break;
        }
        lastEventY = event.getY();
        return true;
    }

    private int getCurrentY() {
        return (int) -scrollY;
    }

    @SuppressWarnings("deprecation")
    private void fling() {
        velocityTracker.computeCurrentVelocity(1000,
                ViewConfiguration.getMaximumFlingVelocity());
        float velocityY = velocityTracker.getYVelocity();
        int currentY = getCurrentY();
        int duration = Step.DEFAULT_DURATION;
        int distanceY = (int) (velocityY / 1000 * duration);
        int direction = velocityY > 0 ? -1 : 1;
        int maxDistance = (int) (velocityY > 0 ? Math.abs(this.scrollY)
                : this.contentHeight - Math.abs(this.scrollY));
        distanceY = direction * Math.min(Math.abs(distanceY), maxDistance);
        if (Math.abs(velocityY) < ViewConfiguration.getMinimumFlingVelocity()) {
            scrollToRightPosition();
            return;
        }
        smoothScrollBy(0, currentY, 0, distanceY, duration);
        scrollToRightPositionDelayed(duration);
    }

    private void scrollToRightPositionDelayed(int duration) {
        postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollToRightPosition();
            }
        }, duration);
    }

    private void scrollToRightPosition() {
        int index = getCurrentItemIndex();
        int position = (int) getItemPosition(index);
        smoothScrollTo(0, -(int) scrollY, 0, position, 250);
    }

    @Override
    public void doScrollTo(int x, int y) {
        scrollY = y;
        makeSureScrollVisible();
    }

    private void makeSureScrollVisible() {
        if (scrollY > this.lineSplitHeight)
            scrollY = this.lineSplitHeight;
        else if (scrollY < -(this.contentHeight))
            scrollY = -(this.contentHeight);
    }

    /**
     * 获取当前选择的条目索引
     *
     * @return
     */
    public int getCurrentItemIndex() {
        int position;
        if (scrollY > 0) {
            position = 0;
            return position;
        }
        position = (int) (Math.abs(scrollY) / itemHeight);
        int reminer = (int) (Math.abs(scrollY) % itemHeight);
        if (reminer > itemHeight / 2)
            position++;
        if (position > this.adapter.getCount() - 1)
            position = this.adapter.getCount() - 1;
        return position;
    }

    public String getCurrentItemString() {
        return this.adapter.getItem(getCurrentItemIndex());
    }

    public int getCurrentValue() {
        return this.adapter.getValue(getCurrentItemIndex());
    }

    /**
     * 获取指定条目在视图中的位置
     *
     * @param itemIndex
     * @return
     */
    private float getItemPosition(int itemIndex) {
        return this.itemHeight * itemIndex;
    }

    public void select(int index) {
        this.scrollY = -getItemPosition(index < 0 ? 0 : index);
        render();
    }

    public void setCurrentValue(int value) {
        int index = this.adapter.getValueIndex(value);
        select(index < 0 ? 0 : index);
    }

    public void setStartValue(int value) {
        this.adapter.setStartValue(value);
        this.adapter.notifyChanged();
    }
}
