package com.example.user.fragmentbacktask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.user.fragmentbacktask.view.BaseLayout;
import com.example.user.fragmentbacktask.view.PageLoadingView;


public class BaseNewActivity extends Activity implements View.OnClickListener {

    protected BaseLayout baseLayout;

    protected View more;
    private TextView tv_more_text;
    private PageLoadingView plv_loading_more;
    protected static final int LAYOUT_TYPE_NORMAL = 1;
    protected static final int LAYOUT_TYPE_HEADER_PROGRESS = 2;

    protected void setView(int layoutResId,int type) {
        baseLayout = new BaseLayout(this, layoutResId,type);
        setContentView(baseLayout);
        if (baseLayout != null) {
            if (baseLayout.tv_right != null)
                baseLayout.tv_right.setOnClickListener(this);
            if (baseLayout.iv_back != null)
                baseLayout.iv_back.setOnClickListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                handleHeaderEvent();
                break;
            case R.id.btn_refresh:
                handleOnClickProgress();
                break;
        }
    }

    /**
     * 如果统一设置progress，处理业务逻辑，子类必须重写此方法
     */
    protected void handleOnClickProgress() {
        onPreExecuteProgress();
    }

    /**
     * 预处理progressbg
     */
    protected void onPreExecuteProgress() {
        baseLayout.progressbg.setVisibility(View.VISIBLE);
        baseLayout.plv_loading.setVisibility(View.VISIBLE);
        baseLayout.tv_load_error.setVisibility(View.INVISIBLE);
        baseLayout.btn_refresh.setVisibility(View.INVISIBLE);
    }

    /**
     * 加载成功处理progressbg
     */
    protected void onPostExecuteProgress() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(400);
        baseLayout.progressbg.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) { // 动画结束时执行此方法
                baseLayout.progressbg.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 加载失败处理progressbg
     */
    protected void onExecuteProgressError() {
        baseLayout.plv_loading.stopAnimation();
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        baseLayout.btn_refresh.startAnimation(animation);
        baseLayout.tv_load_error.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) { // 动画结束时执行此方法
                baseLayout.btn_refresh.setVisibility(View.VISIBLE);
                baseLayout.tv_load_error.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 预处理MoreView
     */
    protected void onPreExecuteMoreView() {
        plv_loading_more.startAnimation();
        plv_loading_more.setVisibility(View.VISIBLE);
        tv_more_text.setText(R.string.downloading);
    }

    /**
     * 滑动刷新失败moreview处理
     */
    protected void onScrollMoreViewFailed() {
        plv_loading_more.startAnimation();
        plv_loading_more.setVisibility(View.GONE);
        tv_more_text.setText("加载失败，上滑重新加载");
    }

    /**
     * 处理完MoreView
     */
    protected void onExecuteMoreView() {
        plv_loading_more.setVisibility(View.GONE);
        tv_more_text.setText(R.string.more);
    }

    protected void setHeaderBar(String title) {
        baseLayout.setTitleAndButton(title, "");
    }

    protected void setHeaderBar(String title, String btn_text) {
        baseLayout.setTitleAndButton(title, btn_text);
    }

    protected void handleHeaderEvent() {
    }
}
