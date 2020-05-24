package com.example.user.fragmentbacktask.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.renderscript.Sampler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

public class AnimaTextViewActivity extends AppCompatActivity {

    private TextView tv_ad_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima_text_view);
        initView();
        showSlideAnim();
    }
    double count = 1;
    private void initView() {

        tv_ad_content = (TextView) findViewById(R.id.tv_ad_content);
        Button btn_change_int = (Button) findViewById(R.id.btn_change_int);
        btn_change_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textAnima(4.3f);
            }
        });
    }

    private void showSlideAnim() {
        int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        tv_ad_content.measure(width,height);
        int startY = 0;
        int endY = -tv_ad_content.getMeasuredHeight();
        PropertyValuesHolder pvh_tran = PropertyValuesHolder.ofFloat("translationY",startY, endY);
        PropertyValuesHolder pvh_alpha= PropertyValuesHolder.ofFloat("alpha",1f,0f);

//        ObjectAnimator.ofPropertyValuesHolder(
//                tv_ad_content,pvh_tran,pvh_alpha
//        ).setDuration(1500).start();


//        ObjectAnimator anim_tran = ObjectAnimator.ofFloat(tv_ad_content,"translationY",startY, endY);
//        anim_tran.setInterpolator(new AccelerateInterpolator(1.3f));

//        ObjectAnimator anim_alpha = ObjectAnimator.ofFloat(tv_ad_content,"alpha",1f, 0f);
//        anim_tran.setInterpolator(new AccelerateInterpolator(1.3f));
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(anim_tran).with(anim_alpha);
//        animatorSet.setDuration(1000);
//        animatorSet.start();
    }

    //测试scrollto
    private void textAnima(float inter) {
        int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        tv_ad_content.measure(width,height);
        int startY = 0;
        int endY =tv_ad_content.getMeasuredHeight();
        tv_ad_content.setText("测试文字\n测试文字");

        ValueAnimator animator = ValueAnimator.ofFloat(startY,endY/2-4);
        animator.setObjectValues("translationY");
        animator.setDuration(1000);
        //x^(2*fractor)
        animator.setInterpolator(new AccelerateInterpolator(inter));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curry = (float) animation.getAnimatedValue();
                Log.i("curry",curry+"");
                tv_ad_content.scrollTo(0,(int) curry);
            }
        });
        animator.start();
    }
}
