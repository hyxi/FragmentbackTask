package com.example.user.fragmentbacktask.view.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/6/29.
 */
public class CustomCanvasView extends View {
    private Paint mPaint;
    private Rect mRect, mBoundRect;

    private int mWidth;
    private int mHeight;

    public CustomCanvasView(Context context) {
        this(context, null);

    }

    public CustomCanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
//        String str = "Hello Android!";
//        mBoundRect = new Rect();//获取字体宽高
//        mPaint.getTextBounds(str,0,str.length(),mBoundRect);

        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rotate(canvas);
    }

    private void rotate(Canvas canvas) {

        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = getPaddingRight() + mWidth;
        mRect.bottom = getPaddingTop() + mHeight;
        //        drawRotate(canvas);

        //圆心
        float x = 150;
        float y = 150;
        //画圆
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(x, y, 100, mPaint);

        Paint tempPaint = new Paint();
        tempPaint.setColor(Color.YELLOW);
        tempPaint.setAntiAlias(true);
        tempPaint.setStrokeWidth(2);

        Paint textPaint = new Paint();
        tempPaint.setAntiAlias(true);
        tempPaint.setTextSize(16);

        Paint innerTextPaint = new Paint();
        innerTextPaint.setAntiAlias(true);
        innerTextPaint.setTextSize(18);
        Path path = new Path();
        RectF rectF = new RectF(x - 75, y - 75, x + 75, y + 75);
        path.addArc(rectF, -125, 90);
        canvas.drawTextOnPath("hello world", path, 0, 0, innerTextPaint);

        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine((float) (x + 102 * Math.sin(Math.PI * 6 * i / 180)),
                        (float) (y - 102 * Math.cos(Math.PI * 6 * i / 180)),
                        (float) (x + 115 * Math.sin(Math.PI * 6 * i / 180)),
                        (float) (y - 115 * Math.cos(Math.PI * 6 * i / 180)), mPaint);

                canvas.save();
                canvas.rotate(6 * i, x, y);
                canvas.drawText(i + "", x - 5, y - 120, textPaint);
                canvas.restore();

            } else {
                canvas.drawLine((float) (x + 102 * Math.sin(Math.PI * 6 * i / 180)),
                        (float) (y - 102 * Math.cos(Math.PI * 6 * i / 180)),
                        (float) (x + 108 * Math.sin(Math.PI * 6 * i / 180)),
                        (float) (y - 108 * Math.cos(Math.PI * 6 * i / 180)), tempPaint);
            }
        }

        Paint txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setTextSize(20);
        //画文字  指定位置
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        Log.i("fontMetr", "ascent://" + fontMetrics.ascent + "bottom://" + fontMetrics.bottom +
                "top://" + fontMetrics.top + "descent://" + fontMetrics.descent);
        Log.i("fontMetr", "x://" + x + "y://" + y);
        String text = "agdh";
        float mStringWidth = txtPaint.measureText(text);
        canvas.drawText(text, x-mStringWidth/2, y, txtPaint);
        canvas.drawLine(x-mStringWidth/2, y+fontMetrics.bottom, x+mStringWidth/2, y+fontMetrics.bottom, tempPaint);




        DrawCur(canvas);

//        canvas.save();
//        canvas.translate(0,0);//把当前画布的原点移到(50,50),后面的操作都以(50,50)作为参照点，
//
//        Paint trPaint = new Paint();
//        trPaint.setColor(Color.BLUE);
//        trPaint.setAntiAlias(true);
//        trPaint.setStrokeWidth(3f);
//
//        canvas.drawCircle(0,0,100,trPaint);
//        canvas.restore();//解除锁定

    }

    private void DrawCur(Canvas canvas) {
        Paint curvePaint = new Paint() {
            {
                setAntiAlias(true);
                setStrokeWidth(2.0f);
                setStyle(Style.STROKE);
                setStrokeCap(Cap.ROUND);
                setColor(Color.RED);
                // 防抖动
                setDither(true);
            }
        };

        Paint pointPaont = new Paint() {{
            setAntiAlias(true);
            setStrokeWidth(10.0f);
            setColor(Color.RED);
            setStyle(Style.STROKE);
            setStrokeCap(Cap.SQUARE);//设置画笔样式
            /**
             *  setSrokeJoin(Paint.Join join);
             设置绘制时各图形的结合方式，如平滑效果等

             画文本
             * setFakeBoldText(boolean fakeBoldText);
             * 模拟实现粗体文字，设置在小字体上效果会非常差
             *  * setTextSkewX(float skewX);
             * 设置斜体文字，skewX为倾斜弧度
             * * setTypeface(Typeface typeface);
             * 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
             */

        }};
        //画贝塞尔曲线
        Path curPath = new Path();
        curPath.reset();
        curPath.moveTo(50, 400);
        curPath.quadTo(150, 260, 260, 600);
        canvas.drawPath(curPath, curvePaint); //画路径
        canvas.drawPoint(150, 260, pointPaont); //画辅助点

        //给出多个点，画贝塞尔曲线
        initPoints();
        initMidPoints(this.mPoints);
        initMidMidPoints(this.mMidPoints);
        initControlPoints(this.mPoints, this.mMidPoints, this.mMidMidPoints);

        mPath = new Path();

        // 画原始点
        drawPoints(canvas);
        // 画穿越原始点的折线
        drawCrossPointsBrokenLine(canvas);
        // 画中间点
        drawMidPoints(canvas);
        // 画中间点的中间点
        drawMidMidPoints(canvas);
        // 画控制点
        drawControlPoints(canvas);
        // 画贝塞尔曲线
        drawBezier(canvas);
    }

    /**
     * 画原始点
     */
    private void drawPoints(Canvas canvas) {
        mPaint.setStrokeWidth(POINTWIDTH);
        for (int i = 0; i < mPoints.size(); i++) {
            canvas.drawPoint(mPoints.get(i).x, mPoints.get(i).y, mPaint);
        }
    }

    private static final float LINEWIDTH = 2.0f;
    private static final float POINTWIDTH = 10.0f;
    /**
     * 即将要穿越的点集合
     */
    private List<Point> mPoints = new ArrayList<>();
    /**
     * 中点集合
     */
    private List<Point> mMidPoints = new ArrayList<>();
    /**
     * 中点的中点集合
     */
    private List<Point> mMidMidPoints = new ArrayList<>();
    /**
     * 移动后的点集合(控制点)
     */
    private List<Point> mControlPoints = new ArrayList<>();

    private Path mPath;

    /**
     * 添加即将要穿越的点
     */
    private void initPoints() {
        int pointWidthSpace = 100;
        int pointHeightSpace = 100;
        for (int i = 0; i < 5; i++) {
            Point point;
            // 一高一低五个点
            if (i % 2 != 0) {
                point = new Point((int) (pointWidthSpace * (i + 0.5)), 500 - pointHeightSpace);
            } else {
                point = new Point((int) (pointWidthSpace * (i + 0.5)), 500);
            }
            mPoints.add(point);
        }
    }

    /**
     * 初始化中点集合
     */
    private void initMidPoints(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            Point midPoint = null;
            if (i == points.size() - 1) {
                return;
            } else {
                midPoint = new Point((points.get(i).x + points.get(i + 1).x) / 2, (points.get(i).y + points.get(i + 1).y) / 2);
            }
            mMidPoints.add(midPoint);
        }
    }

    /**
     * 初始化中点的中点集合
     */
    private void initMidMidPoints(List<Point> midPoints) {
        for (int i = 0; i < midPoints.size(); i++) {
            Point midMidPoint = null;
            if (i == midPoints.size() - 1) {
                return;
            } else {
                midMidPoint = new Point((midPoints.get(i).x + midPoints.get(i + 1).x) / 2, (midPoints.get(i).y + midPoints.get(i + 1).y) / 2);
            }
            mMidMidPoints.add(midMidPoint);
        }
    }

    /**
     * 初始化控制点集合
     */
    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midMidPoints) {
        for (int i = 0; i < points.size(); i++) {
            if (i == 0 || i == points.size() - 1) {
                continue;
            } else {
                Point before = new Point();
                Point after = new Point();
                before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
                before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
                after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
                after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
                mControlPoints.add(before);
                mControlPoints.add(after);
            }
        }
    }

    /**
     * 画穿越原始点的折线
     */
    private void drawCrossPointsBrokenLine(Canvas canvas) {
        mPaint.setStrokeWidth(LINEWIDTH);
        mPaint.setColor(Color.RED);
        // 重置路径
        mPath.reset();
        // 画穿越原始点的折线
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        for (int i = 0; i < mPoints.size(); i++) {
            mPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画中间点
     */
    private void drawMidPoints(Canvas canvas) {
        mPaint.setStrokeWidth(POINTWIDTH);
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < mMidPoints.size(); i++) {
            canvas.drawPoint(mMidPoints.get(i).x, mMidPoints.get(i).y, mPaint);
        }
    }

    /**
     * 画中间点的中间点
     */
    private void drawMidMidPoints(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        for (int i = 0; i < mMidMidPoints.size(); i++) {
            canvas.drawPoint(mMidMidPoints.get(i).x, mMidMidPoints.get(i).y, mPaint);
        }

    }

    /**
     * 画控制点
     */
    private void drawControlPoints(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        // 画控制点
        for (int i = 0; i < mControlPoints.size(); i++) {
            canvas.drawPoint(mControlPoints.get(i).x, mControlPoints.get(i).y, mPaint);
        }
    }


    private void drawBezier(Canvas canvas) {
        mPaint.setStrokeWidth(LINEWIDTH);
        mPaint.setColor(Color.BLACK);
        // 重置路径
        mPath.reset();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {// 第一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);
            } else if (i < mPoints.size() - 2) {// 三阶贝塞尔
                mPath.cubicTo(mControlPoints.get(2 * i - 1).x, mControlPoints.get(2 * i - 1).y,// 控制点
                        mControlPoints.get(2 * i).x, mControlPoints.get(2 * i).y,// 控制点
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            } else if (i == mPoints.size() - 2) {// 最后一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(mControlPoints.size() - 1).x, mControlPoints.get(mControlPoints.size() - 1).y,
                        mPoints.get(i + 1).x, mPoints.get(i + 1).y);// 终点
            }
        }
        canvas.drawPath(mPath, mPaint);
    }

    private void drawRotate(Canvas canvas) {

        mPaint.setAntiAlias(true);                           //设置画笔为无锯齿
        mPaint.setColor(Color.BLACK);                        //设置画笔颜色
        mPaint.setTextSize((float) 30.0);                    //设置字体大小
        canvas.drawColor(Color.WHITE);                      //白色背景

        canvas.clipRect(50, 50, 400, 600);                  //设置裁剪区
        canvas.save();                                  //锁定画布 float degrees, float px, float py
        canvas.rotate(45, 100, 100);                        //旋转45
        mPaint.setColor(Color.BLUE);                     //设置画笔颜色
        canvas.drawText("Hello Android!", 100, 170, mPaint); //绘制字符串
        canvas.restore();                                   //解除锁定

        Log.i("tag", "left" + mRect.left + "//top" + mRect.top);
        mPaint.setColor(Color.RED);                          //设置画笔颜色
        canvas.drawText("Hello Android!", 100, 170, mPaint); //绘制字符串
        RectF oval = new RectF();                         //RectF对象
        oval.left = 100;                                  //左边
        oval.top = 50;                                       //上边
        oval.right = 220;                                 //右边
        oval.bottom = 150;                                    //下边
        canvas.drawOval(oval, mPaint);
    }
}
