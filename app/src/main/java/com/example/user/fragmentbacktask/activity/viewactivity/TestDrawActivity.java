package com.example.user.fragmentbacktask.activity.viewactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.SumPathEffect;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.customview.CustomCanvasView;
import com.example.user.fragmentbacktask.view.CustomVoiceView;
import com.example.user.fragmentbacktask.view.customview.JellyTextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestDrawActivity extends AppCompatActivity {

    private JellyTextView textView;
    private TextView tv_anima;
    CustomCanvasView tv_draw;
    private CustomVoiceView vv_num;
    private final int MSG_UP = 0;
    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_draw);
        initView();
        registerLister();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UP:
                    try {
                        if(count !=vv_num.getMaxNum()) {
                            Thread.sleep(500);
                            vv_num.up();
                            sendEmptyMessage(MSG_UP);
                            count++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void registerLister() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textView.springBack();
            }
        });

        tv_anima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(TestDrawActivity.this,"tv_anima",Toast.LENGTH_SHORT).show();
            }
        });

        vv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(MSG_UP);
            }
        });

    }


    private void showAnima(final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setDuration(2000);
        animation.setStartOffset(0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = tv_anima.getLeft() + (int) (p2 - p1);
                int top = tv_anima.getTop();
                int width = tv_anima.getWidth();
                int height = tv_anima.getHeight();
                tv_anima.clearAnimation();
                tv_anima.layout(left, top, left + width, top + height);
            }
        });
        tv_anima.startAnimation(animation);
    }

    private void initView() {
        textView = (JellyTextView) findViewById(R.id.textview);
        tv_anima = (TextView) findViewById(R.id.tv_anima);
        vv_num = (CustomVoiceView) findViewById(R.id.vv_num);
        tv_draw = (CustomCanvasView) findViewById(R.id.tv_draw);
        showAnima(0,100);
    }


    class SimpleView extends View{
        private Bitmap mBitmap;
        private int limitLength = 0;
        private int width;
        private int heigth;
        private static final int CLIP_HEIGHT = 30;

        private boolean status = HIDE;//显示还是隐藏的状态，最开始为HIDE
        private static final boolean SHOW = true;//显示图片
        private static final boolean HIDE = false;//隐藏图片

        public SimpleView(Context context) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.header_bg);
            limitLength = width = mBitmap.getWidth();
            heigth = mBitmap.getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Region region = new Region();
            int i = 0;
            while (i * CLIP_HEIGHT <= heigth) {//计算clip的区域
                if (i % 2 == 0) {
                    region.union(new Rect(0, i * CLIP_HEIGHT, limitLength, (i + 1) * CLIP_HEIGHT));
                } else {
                    region.union(new Rect(width - limitLength, i * CLIP_HEIGHT, width, (i + 1)
                            * CLIP_HEIGHT));
                }
                i++;
            }


//            canvas.clipRegion(region);
            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
            if (status == HIDE) {//如果此时是隐藏
                limitLength -= 5;
                if(limitLength<=0)
                    status=SHOW;
            } else {//如果此时是显示
                limitLength += 5;
                if(limitLength>=width)
                    status=HIDE;
            }

            invalidate();
        }
    }


    class MyView extends View {
        float phase;
        PathEffect[] effects = new PathEffect[7];
        int[] colors;
        private Paint paint;
        Path path;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            //创建,并初始化Path
            path = new Path();
            path.moveTo(0, 0);
            for (int i = 1; i <= 15; i++) {
                //生成15个点,随机生成它们的坐标,并将它们连成一条Path
                path.lineTo(i * 20, (float) Math.random() * 60);
            }
            //初始化七个颜色
            colors = new int[]{
                    Color.BLACK, Color.BLUE, Color.CYAN,
                    Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW
            };
        }

        protected void onDraw(Canvas canvas) {
            //将背景填充成白色
            canvas.drawColor(Color.WHITE);
            //-------下面开始初始化7中路径的效果
            //使用路径效果
            effects[0] = null;
            //使用CornerPathEffect路径效果
            effects[1] = new CornerPathEffect(10);
            //初始化DiscretePathEffect
            effects[2] = new DiscretePathEffect(3.0f, 5.0f);
            //初始化DashPathEffect
            effects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, phase);
            //初始化PathDashPathEffect
            Path p = new Path();
            p.addRect(0, 0, 8, 8, Path.Direction.CCW);
            effects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE);
            //初始化PathDashPathEffect
            effects[5] = new ComposePathEffect(effects[2], effects[4]);
            effects[6] = new SumPathEffect(effects[4], effects[3]);
            //将画布移到8,8处开始绘制
            canvas.translate(8, 8);
            //依次使用7中不同路径效果,7种不同的颜色来绘制路径
            for (int i = 0; i < effects.length; i++) {
                paint.setPathEffect(effects[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path, paint);
                canvas.translate(0, 60);
            }
            //改变phase值,形成动画效果
            phase += 1;
            invalidate();
        }
    }
}
