package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.BitmapUtils;
import com.example.user.fragmentbacktask.utils.ImageUtils;

/**
 * Created by user on 2016/6/29.
 */
public class CustomTitleView extends View {

    private Rect rect;
    private Bitmap mImage;
    private int mImageScale;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    private int width;
    private int height;

    public CustomTitleView(Context context) {
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获得我们所定义的自定义样式属性
        TypedArray a =context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,
                defStyleAttr,0);
        for (int i = 0; i < a.getIndexCount(); i++)
        {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomTitleView_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_testTextColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        rect = new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mBound = new Rect();
        //计算文字所在矩形，可以得到宽高
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

    }
//    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
//
//    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
//
//    UNSPECIFIED：表示子布局想要多大就多大，很少使用
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
//            int desire = getPaddingLeft() + mBound.width() + getPaddingRight();
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mBound.width();
            if (widthMode == MeasureSpec.AT_MOST){// wrap_content
                int desire = Math.max(desireByImg, desireByTitle);
                width = Math.min(desire, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {// wrap_content
            int desire = getPaddingTop() + mBound.height()+mImage.getHeight()+ getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST){// wrap_content
                height = Math.min(desire, heightSize);
            }
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mTitleTextColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);


        rect.left = getPaddingLeft();
        rect.right = width - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = height - getPaddingBottom();

        if (mBound.width() > width) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float) width - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), height - getPaddingBottom(), mPaint);

        } else {
            //正常情况，将字体居中
            canvas.drawText(mTitleText, width / 2 - mBound.width() * 1.0f / 2, height - getPaddingBottom(), mPaint);
        }

        //取消使用掉的快
        rect.bottom -= mBound.height();

        if (mImageScale == 0) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            //计算居中的矩形范围
            rect.left = width / 2 - mImage.getWidth() / 2;
            rect.right = height / 2 + mImage.getWidth() / 2;
            rect.top = (height - mBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (height - mBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }

    }
}
