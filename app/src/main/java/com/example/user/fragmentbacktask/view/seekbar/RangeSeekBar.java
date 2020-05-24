package com.example.user.fragmentbacktask.view.seekbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyuxi on 2019-06-13
 */
public class RangeSeekBar extends View {
    private static final int COLOR_GRAY = 0xFFD7D7D7;
    public static final int COLOR_BG_GRAY = 0xFFF0F0F0;
    private static final int COLOR_PURPLE = 0xFF6871DC;
    public static final int POINT_RADIUS = StringUtils.dp2px(4);

    // 停止滑动
    public static final int STATE_IDLE = 0;
    // 手指拖拽
    public static final int STATE_DRAGGING = 1;
    // 手指动画
    public static final int STATE_ANIMATED = 2;
    // 开始拖动
    public static final int STATE_START = 3;

    private OnScrollChangeListener mScrollChangeListener;

    private Paint paint;
    private int width;
    private int height;
    private SeekBar seekBar;
    private int left, right, top;
    private int currentIndex = 0;
    private int lineLength;
    private int lineOffset;

    private List<ItemRangeData> mData;
    private List<String> labels;
    private List<Float> percentList;
    private List<Float> stepList;
    private List<String> numList;
    private Path bgPath = new Path();
    private TextPaint textPaint = new TextPaint();
    private Path progressPath = new Path();
    private ValueAnimator animator;


    private boolean mIsDragging;

    private float perX;
    private float perLeft;

    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        height = StringUtils.dp2px(50);

        seekBar = new SeekBar(getContext(), this);

        textPaint.setAntiAlias(true);
        lineOffset = StringUtils.dp2px(20);

        percentList = new ArrayList<>();
        stepList = new ArrayList<>();
    }

    public interface OnScrollChangeListener {
        void onScrollStateChanged(int state, float progress);
    }

    public void setScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mScrollChangeListener = onScrollChangeListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        left = StringUtils.dp2px(10);
        right = w - lineOffset - seekBar.getBarHeight() / 2;
        top = height / 2 - seekBar.getBarHeight() / 2 + StringUtils.dp2px(6);

        width = w - getPaddingLeft() - getPaddingRight() - lineOffset;
        if (mData != null && mData.size() > 0) {
            handleData();
        }
        seekBar.onSizeChanged(left, top, right - left);
    }

    public void setLabels(List<String> labelArray) {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.clear();
        labels.addAll(labelArray);
        invalidate();
    }

    public void setData(List<ItemRangeData> list) {
        mData = list;
        invalidate();
    }

    private void handleData() {
        float countMonth = 0;
        int unknownCount = 0;
        numList = new ArrayList<>();
        for (ItemRangeData rangeItem : mData) {
            String numText;
            if (rangeItem.getAverageMonth() <= 0) {
                numText = "--";
                unknownCount++;
            } else {
                countMonth += rangeItem.getAverageMonth();
                numText = String.valueOf(rangeItem.getAverageMonth());
            }
            numList.add(numText);
        }
        countMonth = countMonth / (1 - unknownCount / 10f);

        lineLength = width - 2 * POINT_RADIUS * (mData.size() + 1) - lineOffset;
        stepList.add(0f);
        for (ItemRangeData rangeItem : mData) {
            float percent;
            if (rangeItem.getAverageMonth() <= 0) {
                percent = 0.1f;
            } else {
                BigDecimal bigDecimal = new BigDecimal(rangeItem.getAverageMonth() / countMonth);
                percent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            }
            stepList.add(stepList.get(stepList.size() - 1) + percent);
            Logger.d("percent step:" + stepList.get(stepList.size() - 1));
            percentList.add(percent);
        }
        bgPath = getPath(percentList.size());
    }

    private Path getPath(int index) {
        Path path = new Path();
        float left = POINT_RADIUS + lineOffset;
        float top = height / 2f + StringUtils.dp2px(6);
        path.addCircle(left, top, POINT_RADIUS, Path.Direction.CW);
        float right;
        for (int i = 0; i < index; i++) {
            left += POINT_RADIUS;
            right = left + percentList.get(i) * lineLength;
            path.addRect(left, top - StringUtils.dp2px(1), right,
                    top + StringUtils.dp2px(1), Path.Direction.CW);
            path.addCircle(right + POINT_RADIUS, top, POINT_RADIUS, Path.Direction.CW);
            left = right + POINT_RADIUS;
        }
        return path;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textPaint.setTextSize(getContext().getResources().getDimension(R.dimen.ten_text_size));

        setMeasuredDimension(widthMeasureSpec, StringUtils.dp2px(100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData != null && mData.size() > 0) {
            paint.setColor(COLOR_BG_GRAY);
            canvas.drawPath(bgPath, paint);
            drawLabels(canvas);
            drawNums(canvas);
            paint.setColor(COLOR_PURPLE);
            canvas.drawPath(progressPath, paint);
            seekBar.draw(canvas);
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void computeCurrentIndex(double percent) {
        if (percent == 1) {
            currentIndex = stepList.size() - 1;
            return;
        }
        if (percent == 0) {
            currentIndex = 0;
            return;
        }
        if (currentIndex > 0 && percent <= stepList.get(currentIndex)) {
            currentIndex--;
        } else {
            if (currentIndex < stepList.size() - 1 && percent >= stepList.get(currentIndex + 1)) {
                currentIndex++;
            }
            if (currentIndex == stepList.size() && percent <= 1) {
                currentIndex--;
            }
        }
    }

    private void recoverPosition(float progress) {
        float offset = progress - stepList.get(currentIndex);
        if (currentIndex >= percentList.size()) {
            return;
        }
        float step = percentList.get(currentIndex);
        int position = currentIndex;
        if (offset > step / 2) {
            position++;
        }
        updateProgress(position);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                perX = event.getX();
                perLeft = seekBar.getProgressPercent() * (right - left);
                if (seekBar.collide(event)) {
                    mIsDragging = true;
                    if (mScrollChangeListener != null) {
                        mScrollChangeListener.onScrollStateChanged(STATE_START, seekBar.getProgressPercent());
                    }
                    return true;
                }
                return event.getX() > lineOffset && lineOffset < width;
            case MotionEvent.ACTION_MOVE:
                float percent;
                float x = event.getX();
                float offsetX = x - perX;
                float currentX = perLeft + offsetX;
                if (currentX <= left) {
                    percent = 0;
                } else if (currentX >= right) {
                    percent = 1;
                } else {
                    percent = currentX / (right - left);
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(STATE_DRAGGING, percent);
                }
                perX = x;
                perLeft = currentX;
                seekBar.slide(percent);
                computeCurrentIndex(percent);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    recoverPosition(seekBar.getProgressPercent());
                    mIsDragging = false;
                } else {
                    if (event.getX() - POINT_RADIUS > lineOffset && lineOffset < width - POINT_RADIUS) {
                        float lastOffsetX = event.getX() - lineOffset - POINT_RADIUS;
                        float lastPercent = lastOffsetX / (right - left);
                        changeClickProgress(lastPercent);
                    }
                }
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(STATE_IDLE, seekBar.getProgressPercent());
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void changeClickProgress(float progress) {
        int preIndex = currentIndex;
        int computeIndex = 0;
        if (progress >= 1) {
            computeIndex = stepList.size() - 1;
        } else if (progress > 0) {
            for (int i = 0; i < stepList.size(); i++) {
                float stepValue = stepList.get(i);
                if (progress < stepValue) {
                    computeIndex = i - 1;
                    break;
                }
            }
            float offset = progress - stepList.get(computeIndex);
            if (computeIndex >= percentList.size()) {
                return;
            }
            float step = percentList.get(computeIndex);
            if (offset > step / 2) {
                computeIndex++;
            }
        }
        if (preIndex != computeIndex) {
            updateProgress(computeIndex);
        }
    }

    private synchronized void updateProgress(int position) {
        float targetPercent = stepList.get(position);
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(seekBar.getProgressPercent(), targetPercent);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator(1.2f));
        animator.addUpdateListener(animation -> {
            float percent = (float) animation.getAnimatedValue();
            computeCurrentIndex(percent);
            seekBar.slide(percent);
            if (mScrollChangeListener != null) {
                mScrollChangeListener.onScrollStateChanged(STATE_ANIMATED, percent);
            }
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentIndex = position;
                if (mScrollChangeListener != null) {
                    mScrollChangeListener.onScrollStateChanged(STATE_ANIMATED, targetPercent);
                }
            }
        });
        animator.start();
    }

    // 计算进度
    public synchronized void computeProgressPath() {
        progressPath = getPath(currentIndex);
        float top = height / 2f + StringUtils.dp2px(6);
        if (currentIndex != stepList.size() - 1) {
            float lastPercent = stepList.get(currentIndex);
            float extraLineLength = (seekBar.getProgressPercent() - lastPercent) * (right - left);
            int pLeft = (int) (lineLength * lastPercent) + 2 * POINT_RADIUS * currentIndex + lineOffset;
            int pRight = pLeft + (int) Math.ceil(extraLineLength);
            progressPath.addRect(pLeft, top - StringUtils.dp2px(1), pRight,
                    top + StringUtils.dp2px(1), Path.Direction.CW);
        }
    }

    private void drawNums(Canvas canvas) {
        if (seekBar.getProgressPercent() <= 0) {
            return;
        }
        canvas.save();
        canvas.translate(lineOffset + 2 * POINT_RADIUS, 0f);
        float currentPercent = seekBar.getProgressPercent();
        int selectPos;
        float progress = stepList.get(currentIndex);
        Logger.d("seekbar:" + currentPercent + "//" + progress);
        if (currentPercent > progress) {
            selectPos = currentIndex;
        } else {
            selectPos = currentIndex - 1;
        }
        float ratio = -1;
        if (selectPos >= percentList.size()) {
            selectPos = percentList.size() - 1;
        }
        if (currentPercent > progress) {
            float spaceLength = percentList.get(selectPos);
            ratio = 1 + (currentPercent - progress) * 0.4f / spaceLength;
        } else if (currentPercent == progress) {
            ratio = 1.4f;
        }
        for (int i = 0; i < numList.size(); i++) {
            float itemLength = percentList.get(i) * lineLength;
            String numItem = numList.get(i);
            boolean isUnknowText = "--".equals(numItem);
            int offsetTop = isUnknowText ? StringUtils.dp2px(10) : StringUtils.dp2px(3);
            if (i > selectPos) {
                break;
            } else {
                if (i == selectPos) {
                    if (!"--".equals(numItem)) {
                        numItem += "个月";
                    }
                    textPaint.setColor(COLOR_PURPLE);
                } else {
                    textPaint.setColor(COLOR_GRAY);
                }
            }
            Rect rect = new Rect();
            textPaint.getTextBounds(numItem, 0, numItem.length(), rect);
            if (ratio > 0 && i == selectPos && !isUnknowText) {
                float offsetX = itemLength / 2f - rect.width() * ratio / 2f;
                Path textPath = getItemTextPath(numItem, ratio, offsetX);
                canvas.drawPath(textPath, textPaint);
            } else {
                float tx = (itemLength - rect.width()) / 2f;
                canvas.drawText(numItem, tx, rect.height() + offsetTop, textPaint);
                canvas.translate(itemLength + 2 * POINT_RADIUS, 0);
            }
        }
        canvas.restore();
    }

    private Path getItemTextPath(String text, float scale, float offsetX) {
        Path textPath = new Path();
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        int cy = textBounds.height();
        textPaint.getTextPath(text, 0, text.length(), offsetX, cy, textPath);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        textPath.transform(matrix);
        return textPath;
    }

    // 绘制底部轮次
    private void drawLabels(Canvas canvas) {
        StaticLayout staticLayout;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            StaticLayout.Builder textBuilder = StaticLayout.Builder.obtain(label, 0, label.length(), textPaint,  StringUtils.dp2px(28));
//            textBuilder.setAlignment(Layout.Alignment.ALIGN_NORMAL)
//                    .setLineSpacing(1f, 0f)
//                    .setEllipsizedWidth(0)
//                    .setIncludePad(false);
//            staticLayout = textBuilder.build();
//
//        } else {
//        }
        int textTop = getPaddingTop() + top + StringUtils.dp2px(36);
        canvas.save();
        canvas.translate(getPaddingLeft(), textTop);
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i);
            if (currentIndex == i) {
                textPaint.setColor(COLOR_PURPLE);
            } else {
                textPaint.setColor(COLOR_GRAY);
            }
            float left;
            if (i > 0) {
                Rect rect = new Rect();
                textPaint.getTextBounds(label, 0, label.length(), rect);
                left = percentList.get(i - 1) * lineLength + 2 * POINT_RADIUS;
            } else {
                left = StringUtils.dp2px(15);
            }
            staticLayout = new StaticLayout(label, textPaint, StringUtils.dp2px(28),
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
            canvas.translate(left, 0);
            staticLayout.draw(canvas);
        }
        canvas.restore();
    }
}
