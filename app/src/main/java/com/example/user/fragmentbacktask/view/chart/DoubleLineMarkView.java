package com.example.user.fragmentbacktask.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.SparseArray;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

/**
 * Created by huangyuxi on 2019-06-27
 * Title: 双曲线MarkView
 */
public class DoubleLineMarkView extends MarkerView {
    private TextView tvYear;
    private TextView tvFirstContent;
    private TextView tvSecondContent;
    private SparseArray<DoubleLineInfo> infoMap;

    private Paint mPaint;

    private int currentIndex;
    private boolean isClickTop;
    private float maxY;

    public DoubleLineMarkView(Context context) {
        super(context, 0);
    }

    public DoubleLineMarkView(Context context, List<DoubleLineInfo> lineInfos) {
        super(context, R.layout.view_chart_mark_layout);

        tvYear = findViewById(R.id.tv_year);
        tvFirstContent = findViewById(R.id.tv_first_value);
        tvSecondContent = findViewById(R.id.tv_second_value);
        infoMap = new SparseArray<>();
        for (DoubleLineInfo lineInfo : lineInfos) {
            infoMap.put(lineInfo.getX(), lineInfo);
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    // 设置y 轴最大值
    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int year = (int) e.getX();
        currentIndex = year;
        tvYear.setText(String.valueOf(year));
        DoubleLineInfo currentInfo = infoMap.get(year);
        if (currentInfo != null) {
            isClickTop = currentInfo.getFirstValue() == e.getY();
            String firstValue = currentInfo.getFirstValue() + "%";
            tvFirstContent.setText(firstValue);
            String secondValue = currentInfo.getSecondValue() + "%";
            tvSecondContent.setText(secondValue);
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2f), -getHeight());
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);

        int saveId = canvas.save();
        // translate to the correct position and draw
        canvas.translate(posX + offset.x, 0);
        draw(canvas);
        canvas.restoreToCount(saveId);

        DoubleLineInfo currentInfo = infoMap.get(currentIndex);
        float ratio =  (maxY - currentInfo.getFirstValue()) / (maxY - currentInfo.getSecondValue());
        float otherY = isClickTop ? posY / ratio : posY * ratio;
        if (isClickTop) {
            drawActiveIndicator(canvas, posX, posY, true);
            drawActiveIndicator(canvas, posX, otherY, false);
        } else {
            drawActiveIndicator(canvas, posX, posY, false);
            drawActiveIndicator(canvas, posX, otherY, true);
        }

    }

    private void drawActiveIndicator(Canvas canvas, float x, float y, boolean isTop) {
        int outerColor = isTop ? 0xffFFD1DA : 0xffDBDEF8;
        mPaint.setColor(outerColor);
        canvas.drawCircle(x, y, StringUtils.dp2px(6), mPaint);
        int innerColor = isTop ? 0xffF9607D : 0xff868DE3;
        mPaint.setColor(innerColor);
        canvas.drawCircle(x, y, StringUtils.dp2px(3), mPaint);
    }
}
