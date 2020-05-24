package com.example.user.fragmentbacktask.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.user.fragmentbacktask.R;

public class CustomLayout extends FrameLayout {

    private Context mContext;

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout rootLayout = createRootLayout();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        attachViewToParent(rootLayout, 0, layoutParams);
        ViewPager viewPager = createViewPager();
        rootLayout.addView(viewPager);

        LinearLayout indicatorContainer = createIndicatorContainer();
        rootLayout.addView(indicatorContainer);
    }

    private RelativeLayout createRootLayout() {
        RelativeLayout rootLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(layoutParams);

        View shapeView = new View(mContext);
        RelativeLayout.LayoutParams shapeLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 100
        );
        shapeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        int color = ContextCompat.getColor(mContext, R.color.light_blue);
        shapeView.setBackgroundColor(color);
        rootLayout.addView(shapeView, shapeLayoutParams);
        return rootLayout;
    }
    private ViewPager createViewPager() {
        ViewPager bannerViewPager = new ViewPager(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bannerViewPager.setLayoutParams(layoutParams);
        return bannerViewPager;
    }

    private LinearLayout createIndicatorContainer() {
        LinearLayout indicatorContainer = new LinearLayout(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = 50;
        layoutParams.setMargins(margin, 0, margin, margin);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorContainer.setLayoutParams(layoutParams);
        indicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        return indicatorContainer;
    }

}
