package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huangyuxi on 2019-06-07
 */
public class CrosswiseChart extends View {

    private Path backGroundPath = new Path();
    private Path cropTextPath = new Path();

    private RectF rectF = new RectF();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private float radius;
    private int offsetY;
    private int itemHeight = StringUtils.dp2px(20);
    private List<Path> backgroundPaths = new ArrayList<>();
    private List<Path> innerPaths = new ArrayList<>();
    private List<Path> numPath = new ArrayList<>();
    private List<String> ratioList = new ArrayList<>();
    private int tenTextSize;
    private int twelveTextSize;

    public static final int COLOR_GRAY = 0xFFF9F9F9;
    public static final int COLOR_BLUE = 0xFF777FDF;
    private static final int COLOR_LIGHT_GRAY = 0xFF8A97A6;
    public static final int COLOR_RED = 0xFFF9607D;

    private int offsetLeft;
    private static List<String> labels;
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    static {
        labels = Arrays.asList("成立", "种子/天使", "A轮", "B轮", "C轮", "D轮", "E轮及以上", "IPO");
    }

    public CrosswiseChart(Context context) {
        this(context, null);
    }

    public CrosswiseChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CrosswiseChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radius = StringUtils.dp2px(2);

        Resources res = context.getResources();
        offsetY = itemHeight + StringUtils.dp2px(8);
        offsetLeft = StringUtils.dp2px(48);
        paint.setStyle(Paint.Style.FILL);

        tenTextSize = res.getDimensionPixelSize(R.dimen.ten_text_size);
        twelveTextSize = res.getDimensionPixelSize(R.dimen.twelve_text_size);
        textPaint.setTextSize(tenTextSize);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(COLOR_LIGHT_GRAY);
    }

    public void setNums(List<Integer> nums) {
        ratioList.clear();
        for (int i = 0; i < nums.size(); i++) {
            int num = nums.get(i);
            if (nums.size() > 1 && i < nums.size() - 1) {
                double ratio = nums.get(i + 1) * 100/ (double)num;
                DecimalFormat decimalFormat = new DecimalFormat("#.0");
                ratioList.add(decimalFormat.format(ratio) + "%");
            }
            Path textPath = getItemTextPath(String.valueOf(num), i * offsetY);
            if (i < innerPaths.size()) {
                Path innerPath = innerPaths.get(i);
                Path newInnerPath = new Path();
                newInnerPath.op(innerPath, textPath, Path.Op.DIFFERENCE);
                newInnerPath.op(innerPath, Path.Op.INTERSECT);
                numPath.add(newInnerPath);

                textPath.op(innerPath, Path.Op.DIFFERENCE);
                cropTextPath.addPath(textPath);
            }
        }
        invalidate();
    }

    private Path getItemTextPath(String text, int offset) {
        Path textPath = new Path();
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        int cx = offsetLeft + StringUtils.dp2px(8);
        int cy = (textBounds.height() + itemHeight) / 2 + offset;
        textPaint.getTextPath(text, 0, text.length(), cx, cy, textPath);
        return textPath;
    }

    // 设置内部
    public void setPercentList(List<Double> progressPercentList) {
        if (progressPercentList == null || progressPercentList.size() == 0) {
            return;
        }

        if (backgroundPaths.size() == 0) {
            initBackGroundList();
        }

        innerPaths.clear();
        for (int i = 0; i < progressPercentList.size(); i++) {
            float right = offsetLeft + (float) (width * progressPercentList.get(i));
            int top = i * offsetY;
            int bottom = top + itemHeight;
            Path innerPath = getRoundRectPath(offsetLeft, top, right, bottom, radius);
            innerPaths.add(innerPath);

            if (i < backgroundPaths.size()) {
                Path backPath = backgroundPaths.get(i);
                backPath.op(innerPath, Path.Op.DIFFERENCE);
            }
        }

        backGroundPath.rewind();
        for (int i = 0; i < backgroundPaths.size(); i++) {
            Path backPath = backgroundPaths.get(i);
            backGroundPath.addPath(backPath);
        }

        invalidate();
    }

    private void initBackGroundList() {
        for (int i = 0; i < 8; i++) {
            int top = i * offsetY;
            int bottom = top + itemHeight;
            Path backgroundPath = getRoundRectPath(offsetLeft, top, offsetLeft + width, bottom, radius);
            backgroundPaths.add(backgroundPath);
        }
    }

    public Path getRoundRectPath(float left, float top, float right, float bottom, float radius) {
        Path roundRectPath = new Path();
        rectF.set(left, top, right, bottom);
        roundRectPath.addRoundRect(rectF, radius, radius, Path.Direction.CW);
        return roundRectPath;
    }

    public void setRectPath(Path path, float left, float top, float right, float bottom) {
        rectF.set(left, top, right, bottom);
        path.rewind();
        path.addRect(rectF, Path.Direction.CW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = StringUtils.dp2px(150);
        int height = 9 * (offsetY) + itemHeight;

        // Adding 1 to prevent artifacts
        int windowWidth = offsetLeft + width + 1 + StringUtils.dp2px(62);
        setMeasuredDimension(windowWidth, height + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        textPaint.setTextSize(tenTextSize);
        textPaint.setColor(COLOR_LIGHT_GRAY);
        canvas.save();
        for (String label : labels) {
            canvas.drawText(label, 0, StringUtils.dp2px(14), textPaint);
            canvas.translate(0, StringUtils.dp2px(28));
        }
        canvas.restore();

        paint.setColor(COLOR_GRAY);
        canvas.drawPath(backGroundPath, paint);

        paint.setColor(COLOR_BLUE);
        for (int i = 0; i < numPath.size(); i++) {
            canvas.drawPath(numPath.get(i), paint);
        }

        canvas.drawPath(cropTextPath, textPaint);

        textPaint.setColor(COLOR_RED);
        textPaint.setTextSize(twelveTextSize);
        canvas.save();
        canvas.translate(0, StringUtils.dp2px(14));
        int dx = offsetLeft + width + StringUtils.dp2px(23);
        for (String ratio : ratioList) {
            canvas.drawText(ratio, dx, StringUtils.dp2px(17), textPaint);
            canvas.translate(0, StringUtils.dp2px(28));
        }
        canvas.restore();
    }
}
