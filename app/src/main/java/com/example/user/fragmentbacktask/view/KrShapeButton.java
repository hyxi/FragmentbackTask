package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.user.fragmentbacktask.R;

import androidx.appcompat.widget.AppCompatButton;

public class KrShapeButton extends AppCompatButton {

    public static final int TOP_LEFT = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_RIGHT = 4;
    public static final int BOTTOM_LEFT = 8;

    /**
     * shape模式
     * 矩形（rectangle）、椭圆形(oval)、线形(line)、环形(ring)
     */
    private int mShapeMode = 0;
    private int mFillColor = 0xFFFFFFFF;
    private int mStrokeColor = 0x00000000;
    private int mStrokeWidth = 0;
    private int mCornerRadius = 0;
    private int mCornerPosition = -1;
    /**
     * 渐变方向
     */
    private int mOrientation = 0;
    private int mStartColor = 0;
    private int mEndColor = 0;
    /**
     * 点击动效
     */
    private boolean mActiveEnable = false;
    private int mPressedColor = 0;
    private int mDrawablePosition;
    private int mGravity = -1;
    // 内容总宽度
    private float contentWidth = 0f;
    // 内容总高度
    private float contentHeight = 0f;

    private GradientDrawable normalDrawable;
    private RippleDrawable rippleDrawable;
    private StateListDrawable stateListDrawable;

    public KrShapeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        normalDrawable = new GradientDrawable();
    }

    public KrShapeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        normalDrawable = new GradientDrawable();
    }

    public KrShapeButton(Context context) {
        super(context);
        init(null);
        normalDrawable = new GradientDrawable();
        stateListDrawable = new StateListDrawable();
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = attrs == null ? null : getContext().obtainStyledAttributes(attrs, R.styleable.KrShapeButton);
        if (typedArray != null) {
            mShapeMode = typedArray.getInt(R.styleable.KrShapeButton_kr_shapeMode, 0);
            mFillColor = typedArray.getInt(R.styleable.KrShapeButton_kr_fillColor, 0xFFFFFFFF);
            mStrokeColor = typedArray.getInt(R.styleable.KrShapeButton_kr_strokeColor, 0x00000000);
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.KrShapeButton_kr_strokeWidth, 0);
            mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.KrShapeButton_kr_cornerRadius, 0);
            mStartColor = typedArray.getInt(R.styleable.KrShapeButton_kr_startColor, 0xFFFFFFFF);
            mEndColor = typedArray.getInt(R.styleable.KrShapeButton_kr_endColor, 0xFFFFFFFF);
            mOrientation = typedArray.getInt(R.styleable.KrShapeButton_kr_orientation, 0);
            mActiveEnable = typedArray.getBoolean(R.styleable.KrShapeButton_kr_activeEnable, false);
            mCornerPosition = typedArray.getInt(R.styleable.KrShapeButton_kr_cornerPosition, -1);
            mPressedColor = typedArray.getInt(R.styleable.KrShapeButton_kr_pressColor, 0xFF666666);
            mDrawablePosition = typedArray.getInt(R.styleable.KrShapeButton_kr_drawablePosition, -1);
            mGravity = typedArray.getInt(R.styleable.KrShapeButton_kr_gravity, -1);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int shapeMode;
        switch (mShapeMode) {
            case 0:
                shapeMode = GradientDrawable.RECTANGLE;
                break;
            case 1:
                shapeMode = GradientDrawable.OVAL;
                break;
            case 2:
                shapeMode = GradientDrawable.LINE;
                break;
            case 3:
                shapeMode = GradientDrawable.RING;
                break;
            default:
                shapeMode = GradientDrawable.RECTANGLE;
                break;
        }
        normalDrawable.setShape(shapeMode);
        if (mStartColor != 0xFFFFFFFF && mEndColor != 0xFFFFFFFF) {
            int[] colors = new int[]{mStartColor, mEndColor};
            normalDrawable.setColors(colors);
            GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
            if (mOrientation == 1) {
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            }
            normalDrawable.setOrientation(orientation);
        } else {
            normalDrawable.setColor(mFillColor);
        }

        // 统一设置圆角半径
        if (mCornerPosition == -1) {
            normalDrawable.setCornerRadius(mCornerRadius);
        } else {
            float[] cornerRadii = getCornerRadiusByPosition();
            normalDrawable.setCornerRadii(cornerRadii);
        }
        // 默认的透明边框不绘制,否则会导致没有阴影
        if (mStrokeColor != 0) {
            normalDrawable.setStroke(mStrokeWidth, mStrokeColor);
        }

        if (mActiveEnable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rippleDrawable = new RippleDrawable(ColorStateList.valueOf(mPressedColor), normalDrawable, null);
                setBackground(rippleDrawable);
            } else {
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, normalDrawable);
                stateListDrawable.addState(new int[0], normalDrawable);
                setBackground(stateListDrawable);
            }
        } else {
            setBackground(normalDrawable);
        }
    }

    public void setFillColor(int color) {
        if (!mActiveEnable) {
            normalDrawable.setColor(color);
            setBackground(normalDrawable);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rippleDrawable.setColor(ColorStateList.valueOf(color));
            } else {
                setBackground(normalDrawable);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Drawable[] compoundDrawables = getCompoundDrawables();
        if (mDrawablePosition > -1) {
            Drawable drawable = compoundDrawables[mDrawablePosition];
            if (drawable == null) {
                return;
            }
            int drawablePadding = getCompoundDrawablePadding();
            if (mDrawablePosition == 0 || mDrawablePosition == 2) {
                int drawableWidth = drawable.getIntrinsicWidth();
                int textWidth = (int) getPaint().measureText(getText().toString());
                contentWidth = textWidth + drawableWidth + drawablePadding;
                int rightPadding = (int) (getMeasuredWidth() - contentWidth);
                setPadding(rightPadding / 2, 0, rightPadding / 2, 0);
            } else {
                Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
                // 单行高度
                float singeLineHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
                float totalLineSpaceHeight = (getLineCount() - 1) * getLineSpacingExtra();
                float textHeight = singeLineHeight * getLineCount() + totalLineSpaceHeight;
                contentHeight = textHeight + drawable.getIntrinsicHeight() + drawablePadding;
                int bottomPadding = (int) (getMeasuredHeight() - contentHeight);
                setPadding(0, bottomPadding / 2, 0, bottomPadding / 2);
            }
        }
        int gravity = getCustomGravity();
        setGravity(gravity);
        setClickable(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
        // 去掉button 默认间距
        setPadding(0, 0, 0, 0);
        setMinHeight(0);
        setMinWidth(0);
        changeTintContextWrapperToActivity();
    }

    // 自定义gravity
    private int getCustomGravity() {
        int gravity = Gravity.CENTER;
        if (mGravity != -1) {
            switch (mGravity) {
                case 0:
                    gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                    break;
                case 1:
                    gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                    break;
                case 2:
                    gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                    break;
                case 3:
                    gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                    break;
            }
        }
        return gravity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 图片文字居中
        if (getMeasuredWidth() > 0 && (mDrawablePosition == 0 || mDrawablePosition == 2)) {
            canvas.translate((getWidth() - contentWidth) / 2, 0f);
        }
        if (getMeasuredHeight() > 0 && (mDrawablePosition == 1 || mDrawablePosition == 3)) {
            canvas.translate(0f, (getHeight() - contentHeight) / 2);
        }
    }


    /**
     * 从support23.3.0开始View中的getContext方法返回的是TintContextWrapper而不再是Activity
     * 如果使用xml注册onClick属性，就会通过反射到Activity中去找对应的方法
     * 5.0以下系统会反射到TintContextWrapper中去找对应的方法，程序直接crash
     * 所以这里需要针对5.0以下系统单独处理View中的getContext返回值
     */
    private void changeTintContextWrapperToActivity() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Class clazz = this.getClass();
//            if (clazz != null) {
//                try {
//                    Field field = clazz.getDeclaredField("mContext");
//                    field.setAccessible(true);
//                    field.set(this, it);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                clazz = clazz.getSuperclass();
//            }
////            this.getActivity() =
//        }
    }

    private float[] getCornerRadiusByPosition() {
        float[] positionList = new float[8];
        float cornerRadius = mCornerRadius;
        if (containsFlag(mCornerPosition, TOP_LEFT)) {
            positionList[0] = cornerRadius;
            positionList[1] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, TOP_RIGHT)) {
            positionList[2] = cornerRadius;
            positionList[3] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, BOTTOM_RIGHT)) {
            positionList[4] = cornerRadius;
            positionList[5] = cornerRadius;
        }
        if (containsFlag(mCornerPosition, BOTTOM_LEFT)) {
            positionList[6] = cornerRadius;
            positionList[7] = cornerRadius;
        }
        return positionList;
    }

    private boolean containsFlag(int flagSet, int flag) {
        return (flagSet | flag) == flagSet;
    }
}
