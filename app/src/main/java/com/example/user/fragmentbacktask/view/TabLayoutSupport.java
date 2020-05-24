package com.example.user.fragmentbacktask.view;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;


/**
 * Created by user on 2016/1/15.
 */
public class TabLayoutSupport {
    public interface ViewPagerTabLayoutAdapter {
        String getPageTitle(int position);

        int getItemCount();
    }

    public static void setupWithViewPager(@NonNull TabLayout tabLayout, @NonNull ViewPager viewPager
            , @NonNull ViewPagerTabLayoutAdapter viewPagerTabLayoutAdapter) {
        tabLayout.removeAllTabs();
        int i = 0;

        for (int count = viewPagerTabLayoutAdapter.getItemCount(); i < count; ++i) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPagerTabLayoutAdapter.getPageTitle(i)));

        }
        final TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.addOnPageChangeListener(listener);
        tabLayout.setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        public void onTabSelected(TabLayout.Tab tab) {
            //快速移动
            this.mViewPager.setCurrentItem(tab.getPosition(),false);
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}
