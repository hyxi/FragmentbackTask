package com.example.user.fragmentbacktask.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.activity.TestFlowActivity;
import com.example.user.fragmentbacktask.entity.ADInfo;
import com.example.user.fragmentbacktask.utils.ScreenUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class CycleViewPager extends Fragment implements ViewPager.OnPageChangeListener {

    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private FrameLayout viewPagerFragmentLayout;
    private LinearLayout indicatorLayout; // 指示器
    private BaseViewPager viewPager;
    private BaseViewPager parentViewPager;
    private ViewPagerAdapter adapter;
    private static CycleHandle handler;
    private int time = 3000; // 默认轮播时间
    private int currentPosition = 0; // 轮播当前位置
    private boolean isScrolling = false; // 滚动框是否滚动着
    private boolean isCycle = false; // 是否循环
    private boolean isAutoScroll = false; // 是否轮播
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private static final int WHEEL = 100; // 转动
    private static final int WHEEL_WAIT = 101; // 等待
    private ImageCycleViewListener mImageCycleViewListener;
    private List<ADInfo> infos;
    private LayoutInflater mInflater;
    private ImageView currentIndicator;//当前指示器
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.view_cycle_viewpager_content, null);
        viewPager = (BaseViewPager) view.findViewById(R.id.viewPager);
        indicatorLayout = (LinearLayout) view.findViewById(R.id.layout_viewpager_indicator);
        viewPagerFragmentLayout = (FrameLayout) view.findViewById(R.id.layout_viewager_content);

        handler = new CycleHandle((TestFlowActivity) mContext);
        return view;
    }


    private class CycleHandle extends Handler {
        private final WeakReference<TestFlowActivity> mActivity;

        public CycleHandle(TestFlowActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestFlowActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == WHEEL && imageViews.size() != 0) {
                    if (!isScrolling) {
                        int max = imageViews.size();
                        int position = currentPosition + 1;
                        if (isCycle) {
                            if (position == max) { // 最后一页时回到第一页
                                viewPager.setCurrentItem(0, false);
                            } else {
                                viewPager.setCurrentItem(position, true);
                            }
                        }
                    }

                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                    return;
                }
                if (msg.what == WHEEL_WAIT && imageViews.size() != 0) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                }
            }
        }
    }

    public void setData(List<ADInfo> list, ImageCycleViewListener listener) {
        setData(list, listener, 0);
    }

    /**
     * 初始化viewpager
     *
     * @param showPosition 默认显示位置
     */
    public void setData(List<ADInfo> list, ImageCycleViewListener listener, int showPosition) {
        mImageCycleViewListener = listener;
        infos = list;
        this.imageViews.clear();

        for (ADInfo bean : list) {
            ImageView imageView = (ImageView) mInflater.inflate(
                    R.layout.view_banner, null);
            Glide.with(mContext).load(bean.getUrl()).into(imageView);
            imageViews.add(imageView);
        }
        if (imageViews.size() == 0) {
            viewPagerFragmentLayout.setVisibility(View.GONE);
            return;
        }

        // 初始化指示器
        initIndicator(imageViews.size());

        adapter = new ViewPagerAdapter();
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        if (showPosition < 0 || showPosition >= imageViews.size())
            showPosition = 0;
        viewPager.setCurrentItem(showPosition);

    }


    private void initIndicator(int size) {
        indicatorLayout.removeAllViews();
        if (size != 1) {
            for (int i = 0; i < size; i++) {
                ImageView img = new ImageView(getActivity());
                img.setImageResource(R.mipmap.ad_switcher_btn);
                if (ScreenUtils.screenWidth <= 480) {
                    img.setPadding(10, 0, 0, 0);
                } else {
                    img.setPadding(25, 0, 0, 0);
                }
                indicatorLayout.addView(img);
            }
            setIndicator(0);
        }
    }

    private void setIndicator(int position) {
        if (currentIndicator != null)
            currentIndicator.setImageResource(R.mipmap.ad_switcher_btn);

        currentIndicator = (ImageView) indicatorLayout.getChildAt(position);
        if (currentIndicator == null)
            return;
        currentIndicator.setImageResource(R.mipmap.ad_switcher_btn_selected);
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     *
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * @param isAutoScroll 设置是否轮播，默认不轮播,轮播一定是循环的
     */
    public void setAutoScroll(boolean isAutoScroll) {
        this.isAutoScroll = isAutoScroll;
        if (isAutoScroll) {
            isCycle = true;
            handler.postDelayed(runnable, time);
        } else {
            isCycle = false;
            handler.removeMessages(WHEEL);
        }
    }

    /**
     * 是否处于轮播状态
     */
    public boolean getAutoScroll() {
        return isAutoScroll;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()
                    && isAutoScroll) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
    public void releaseHeight() {
        if (getView() != null)
            getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        refreshData();
    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认5000ms
     *
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 隐藏CycleViewPager
     */
    public void hide() {
        viewPagerFragmentLayout.setVisibility(View.GONE);
    }

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    public BaseViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Byte.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            try {
                container.addView(imageViews.get(position % imageViews.size()), 0);
            } catch (Exception e) {
                //handler something
            }
            return imageViews.get(position % imageViews.size());
//            ImageView v = imageViews.get(position);
//            if (mImageCycleViewListener != null) {
//                v.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        mImageCycleViewListener.onImageClick(infos.get(currentPosition+1), currentPosition+1, v);
//                    }
//                });
//            }
//            container.addView(v);
//            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (arg0 == 0) { // viewPager滚动结束
            if (parentViewPager != null)
                parentViewPager.setScrollable(true);

            releaseTime = System.currentTimeMillis();

            viewPager.setCurrentItem(currentPosition, false);

        }
        isScrolling = false;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        currentPosition = arg0;
        setIndicator(arg0 % imageViews.size());
    }

    /**
     * 设置viewpager是否可以滚动
     */
    public void setScrollable(boolean enable) {
        viewPager.setScrollable(enable);
    }

    /**
     * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
     */
    public int getCurrentPostion() {
        return currentPosition;
    }

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以 阻止父ViewPager滑动事件
     * 父ViewPager需要实现ParentViewPager中的setScrollable方法
     */
    public void disableParentViewPagerTouchEvent(BaseViewPager parentViewPager) {
        if (parentViewPager != null)
            parentViewPager.setScrollable(false);
    }


    /**
     * 轮播控件的监听事件
     */
    public static interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        void onImageClick(ADInfo info, int position, View imageView);
    }
}
