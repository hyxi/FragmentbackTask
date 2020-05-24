package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.user.fragmentbacktask.R;

/**
 * Created by user on 2016/6/29.
 */
public class CustomVoiceView extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private int mCount;
    private Bitmap innerImage;
    private int mSplitSize;
    private int currentCount = 2;//当前进度
    private Paint mPaint;
    private Rect mRect;


    public CustomVoiceView(Context context) {
        this(context,null);
    }

    public CustomVoiceView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a =context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar,
                defStyleAttr,0);
        for (int i = 0; i < a.getIndexCount(); i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomVolumControlBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.CustomVolumControlBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.CustomVolumControlBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumControlBar_dotCount:
                    mCount = a.getInt(attr, 12);
                    break;
                case R.styleable.CustomVolumControlBar_splitSize:
                    mSplitSize = a.getInt(attr,10);
                    break;
                case R.styleable.CustomVolumControlBar_bg:
                    innerImage = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
            }
        }

        a.recycle();
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画外圆
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头

        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径

        /**
         * 画块
         */
        drawOval(canvas, centre, radius);
        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (centre - Math.sqrt(2) * 1.0f / 2 * relRadius) ;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.top + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        canvas.drawBitmap(innerImage, null, mRect, mPaint);
        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (innerImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - innerImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - innerImage.getHeight() * 1.0f / 2);
            mRect.right = mRect.left + innerImage.getWidth();
            mRect.bottom = mRect.top + innerImage.getHeight();

        }
        // 绘图
//        canvas.drawBitmap(innerImage, null, mRect, mPaint);
    }

    private void drawOval(Canvas canvas, int centre, int radius) {
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (360 * 1.0f - mCount*3* mSplitSize/2) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize)+135, itemSize, false, mPaint); // 根据进度画圆弧
        }

        mPaint.setColor(mSecondColor); // 设置圆环的颜色
        for (int i = 0; i < currentCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize)+135, itemSize, false, mPaint); // 根据进度画圆弧
        }
    }

    public void up(){
        currentCount++;
        postInvalidate();
    }

    public int getMaxNum(){
        return mCount;
    }
}
