package com.example.user.fragmentbacktask.activity;

import android.os.Bundle;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.fragment.TabFragment;
import com.example.user.fragmentbacktask.view.SimpleViewPagerIndicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BounceNavLayoutActivity extends AppCompatActivity {

    private String[] mTitles = new String[] { "简介", "评价", "相关" };
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[mTitles.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_nav_layout);

        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);

        for (int i = 0; i < mTitles.length; i++)
        {
            mFragments[i] = (TabFragment) TabFragment.newInstance(mTitles[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initViews() {
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_bounce_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_bounce_viewpager);
    }
}
