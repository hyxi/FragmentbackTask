package com.example.user.fragmentbacktask.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.GridAdapter;
import com.example.user.fragmentbacktask.view.SoufunGridView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class TestAnimaActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_notice_dot,iv_cover;
    private Button btn_show_cover,btn_toast;
    private RelativeLayout ll_main;

    private SoufunGridView gv_test;

    private Context mContext;
    private String telephone = "18500372398";

    long mStartTime = 0;
    boolean isFinish = false;
    boolean isShow = false;
    static final int ANIM_START=0;
    static final int ANIM_FRAME_OPEN=1;
    static final int ANIM_FRAME_CLOSE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anima);
        initView();
        initData();
        registerListener();
    }

    private void initView() {
        iv_notice_dot = (ImageView) findViewById(R.id.iv_notice_dot);
        gv_test = (SoufunGridView) findViewById(R.id.gv_test);

        ll_main = (RelativeLayout) findViewById(R.id.ll_main);

        btn_show_cover = (Button) findViewById(R.id.btn_show_cover);
        btn_toast = (Button) findViewById(R.id.btn_toast);
        btn_show_cover.setOnClickListener(this);
        btn_toast.setOnClickListener(this);
    }

    private void initData(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList
                ("业主论坛","看房团","资讯","商业地产","我要贷款","帮你找房"));
        GridAdapter adapter = new GridAdapter(this,list);
        gv_test.setAdapter(adapter);
    }

    private void registerListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_show_cover:
                //使用handler实现
//                mHandler.sendEmptyMessage(ANIM_START);
                showCoverAnima();
                break;
            case R.id.btn_toast:
                mHandler.sendEmptyMessage(ANIM_START);
                break;
        }
    }

    private void showCoverAnima(){

        float btn_height = btn_show_cover.getHeight();

        float iv_height = gv_test.getHeight();

        ValueAnimator animator = isShow? ValueAnimator.ofFloat(iv_height,0):
                ValueAnimator.ofFloat(0,iv_height);
        isShow = !isShow;
        animator.setTarget(gv_test);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.i("currentValue",currentValue+"");
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_main.getLayoutParams();
                layoutParams.height = (int)currentValue;
                ll_main.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator(1.5f));
        animator.start();
    }

    private Handler mHandler = new Handler(){
        int delaytime=20;
        final int mDuration = 400;
        LinearLayout.LayoutParams layoutParams;
        DecelerateInterpolator mInterpolator = new DecelerateInterpolator(1.5f);
        int slideResult = 0;

        private int getY(int height) {
            int passTime = (int) (AnimationUtils.currentAnimationTimeMillis() - mStartTime);
            if(passTime>mDuration){
                return height;
            }
            float math = 1f / (float) mDuration;
            float x = mInterpolator.getInterpolation(passTime * math);
            slideResult = Math.round(height * x);
            Log.i("distance",slideResult+"");
            return slideResult;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ANIM_START:
                    mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    isFinish = false;
                    sendEmptyMessage(isShow?ANIM_FRAME_CLOSE:ANIM_FRAME_OPEN);
                    break;

                case ANIM_FRAME_OPEN:
                    layoutParams = (LinearLayout.LayoutParams) ll_main.getLayoutParams();

                    layoutParams.height = getY(gv_test.getHeight());
                    if(layoutParams.height >= gv_test.getHeight()){
                        isShow = true;
                        isFinish = true;
                    }
                    ll_main.setLayoutParams(layoutParams);
                    if(!isFinish){
                        sendEmptyMessageDelayed(ANIM_FRAME_OPEN,delaytime);
                    }
                    break;

                case ANIM_FRAME_CLOSE:
                    layoutParams = (LinearLayout.LayoutParams) ll_main.getLayoutParams();

                    layoutParams.height = gv_test.getHeight()-getY(gv_test.getHeight());
                    if(layoutParams.height ==0){
                        isShow = false;
                        isFinish = true;
                    }
                    ll_main.setLayoutParams(layoutParams);
                    if(!isFinish){
                        sendEmptyMessageDelayed(ANIM_FRAME_CLOSE,delaytime);
                    }
                    break;
            }
        }
    };

    private void computeValue(){
        int btn_right = btn_show_cover.getRight();
        int btn_left = btn_show_cover.getLeft();
        int btn_top = btn_show_cover.getTop();
        int btn_height = btn_show_cover.getHeight();

        int iv_height = gv_test.getHeight();
        Log.i("tag","btn_right://"+btn_right+""+"btn_left://"+btn_left
                +"btn_top://"+btn_top+"btn_height://"+btn_height
                +"iv_height://"+iv_height);
    }




}
