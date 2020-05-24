package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.utils.StringUtils;

/**
 * Created by huangyuxi on 2019-07-05
 * Title:
 */
public class CustomGraphView extends View {

    private Path trianglePath = new Path();
    private Paint paint = new Paint();
    private Path topPath;

    public CustomGraphView(Context context) {
        this(context, null, 0);
    }

    public CustomGraphView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xff707377);
        paint.setStrokeJoin(Paint.Join.ROUND);

        trianglePath.moveTo(StringUtils.dp2px(20), StringUtils.dp2px(20));
        trianglePath.lineTo(StringUtils.dp2px(40), StringUtils.dp2px(20));
        trianglePath.lineTo(StringUtils.dp2px(30), StringUtils.dp2px(27));
//
        trianglePath.close();
//        topPath = new Path();
//        RectF rectF = new RectF(52, 40, 68, 58);
//        topPath.addArc(rectF, 0, 180);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(trianglePath, paint);
//        canvas.drawPath(topPath, paint);
    }
}
