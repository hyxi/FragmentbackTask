package com.example.user.fragmentbacktask.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by huangyuxi on 2019-06-27
 * Title:
 */
public class BarMarkView extends MarkerView {
    private TextView tvYear;
    private TextView tvFirstContent;
    private Paint mPaint;

    public BarMarkView(Context context) {
        super(context, R.layout.view_chart_mark_layout);
        tvYear = findViewById(R.id.tv_year);
        tvFirstContent = findViewById(R.id.tv_first_value);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(StringUtils.dp2px(1));
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int year = (int) e.getX();
        tvYear.setText(String.valueOf(year));
        tvFirstContent.setText(String.valueOf(e.getY()));
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

        if (posY > StringUtils.dp2px(43)) {
            canvas.drawLine(posX, StringUtils.dp2px(45), posX + 2, posY, mPaint);
        }
    }
}
