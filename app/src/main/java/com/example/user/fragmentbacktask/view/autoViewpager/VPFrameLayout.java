package com.example.user.fragmentbacktask.view.autoViewpager;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;
import com.example.user.fragmentbacktask.utils.ScreenUtils;


/**
 * Created by hyxi
 */
public class VPFrameLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String tag = "VPFrameLayout";

    private Context context;
    private BannerViewPager viewPager;
    private LinearLayout indicatorContainer;
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean isScroll = BannerConfig.IS_SCROLL;
    private int delayTime = BannerConfig.TIME;
    private int scrollTime = BannerConfig.DURATION;
    private int mLayoutResId = R.layout.auto_scroll_layout;
    private int mIndicatorSelectedResId = R.drawable.gray_radius;
    private int mIndicatorUnselectedResId = R.drawable.white_radius;
    private BannerPagerAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private DisplayMetrics dm;
    private int lastPosition = 0;
    private int currentItem;
    private ArrayList<String> adDatas;
    private WeakHandler handler = new WeakHandler();
    private BannerScroller mScroller;
    private int count;
    private ArrayList<ImageView> imageViews;

    public VPFrameLayout(Context context) {
        this(context, null);
    }

    public VPFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VPFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        dm = context.getResources().getDisplayMetrics();
        initView(context, attrs);
    }


   private void initView(Context context,AttributeSet attrs){
       handleTypedArray(context, attrs);
       View view = LayoutInflater.from(context).inflate(mLayoutResId, this, true);
       viewPager = (BannerViewPager) view.findViewById(R.id.viewPager);
       indicatorContainer = (LinearLayout) view.findViewById(R.id.indicator_container);
       initViewPagerScroll();
   }
    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VPLayout);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.VPLayout_indicator_drawable_selected, R.drawable.gray_radius);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.VPLayout_indicator_drawable_unselected, R.drawable.white_radius);
        delayTime = typedArray.getInt(R.styleable.VPLayout_delay_time, BannerConfig.TIME);
        scrollTime = typedArray.getInt(R.styleable.VPLayout_scroll_time, BannerConfig.DURATION);
        isAutoPlay = typedArray.getBoolean(R.styleable.VPLayout_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        mLayoutResId = typedArray.getResourceId(R.styleable.VPLayout_layout_id, mLayoutResId);
        typedArray.recycle();
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(viewPager.getContext());
            mScroller.setDuration(scrollTime);
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            Log.e(tag, e.getMessage());
        }
    }

    public VPFrameLayout isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public VPFrameLayout setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }


    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public VPFrameLayout setOffscreenPageLimit(int limit) {
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }
    /**
     * Set a {@link ViewPager.PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public VPFrameLayout setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public VPFrameLayout setViews(ArrayList<String> adDatas) {
        this.adDatas = adDatas;
        count  = adDatas.size();
        return this;
    }

    public VPFrameLayout start() {
        setViewList(adDatas);
        setData();
        return this;
    }

    public VPFrameLayout setViewList(List<?> list){
        if (list == null || list.size() <= 0) {
            Log.e(tag, "Please set the images data.");
            return null;
        }
        createIndicator();
        return this;
    }

    /**
     * 创建指示器
     */
    private void createIndicator() {
        indicatorContainer.removeAllViews();
        for(int i =0;i<adDatas.size();i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (ScreenUtils.screenWidth <= 480) {
                imageView.setPadding(10, 0, 0, 0);
            } else {
                imageView.setPadding(25, 0, 0, 0);
            }
            if(i == 0){
                imageView.setImageResource(R.mipmap.ad_switcher_btn_selected);
            }else{
                imageView.setImageResource(R.mipmap.ad_switcher_btn);
            }
            indicatorContainer.addView(imageView);
        }
    }

    /**
     * 设置viewpager 数据
     */

    private void setData(){
        currentItem = 1;
        if (adapter == null) {
            imageViews = new ArrayList<>();
            for(int i = 0;i<=count+1;i++){
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT
                );
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String url;
                if (i == 0) {
                    url = adDatas.get(count - 1);
                } else if (i == count + 1) {
                    url = adDatas.get(0);
                } else {
                    url = adDatas.get(i - 1);
                }
                Glide.with(TestApplication.getSelf())
                        .load(url)
                        .into(imageView);
                imageViews.add(imageView);
            }
            adapter = new BannerPagerAdapter();
            viewPager.addOnPageChangeListener(this);
        }
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        if (isScroll && adDatas.size() > 1) {
            viewPager.setScrollable(true);
        } else {
            viewPager.setScrollable(false);
        }
        if (isAutoPlay)
            startAutoPlay();
    }

    public void startAutoPlay() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, delayTime);
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(task);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (count > 1 && isAutoPlay) {
                currentItem = currentItem % (count + 1) + 1;
                Log.i(tag, "curr:" + currentItem + " count:" + count);
                if (currentItem == 1) {
                    viewPager.setCurrentItem(currentItem, false);
                    handler.post(task);
                } else {
                    viewPager.setCurrentItem(currentItem);
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
               startAutoPlay();
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 返回真实的位置
     *
     * @param position
     * @return 下标从0开始
     */
    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            View view = imageViews.get(position);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        ((ImageView)indicatorContainer.getChildAt((lastPosition - 1 + count) % count))
                .setImageResource(R.mipmap.ad_switcher_btn);
        ((ImageView)indicatorContainer.getChildAt((position - 1 + count) % count))
                .setImageResource(R.mipmap.ad_switcher_btn_selected);
        lastPosition = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        currentItem = viewPager.getCurrentItem();
        Log.i(tag,"curren:"+currentItem+"//"+(adDatas.size()-1));
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }
}
