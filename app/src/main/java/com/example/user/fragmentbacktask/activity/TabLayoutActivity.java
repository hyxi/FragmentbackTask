package com.example.user.fragmentbacktask.activity;

import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.fragment.EvaluateNoReplyFragment;
import com.example.user.fragmentbacktask.fragment.EvaluateReplyFragment;
import com.example.user.fragmentbacktask.fragment.SurveyListTabFragment;
import com.example.user.fragmentbacktask.view.TabLayoutSupport;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class TabLayoutActivity extends FragmentActivity {

    private TabLayout tab_layout_title;
    private ViewPager vp_content_fragment;
    private List<String> tab_title;//tab名称
    private List<Fragment> sumView;

    private Fragment views1,views2,views3,views4,views5,views6,views7,views8,views9,views10,views11,
            views12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
        fillData();

        getIntent().getStringExtra("sss");

        setListener();
    }

    private void setListener() {
        vp_content_fragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        tab_layout_title = (TabLayout) findViewById(R.id.tab_layout_title);
        vp_content_fragment = (ViewPager) findViewById(R.id.vp_content_fragment);
    }


    private void fillData() {
        String[] datas = {"热点","北京","视频","订阅","社会","娱乐","科技","要闻","军事","国际","段子"
                ,"趣图"};
        tab_title = new ArrayList<>(Arrays.asList(datas));
        tab_layout_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        views1 = new EvaluateNoReplyFragment();
        views2 = new EvaluateReplyFragment();
        views3 = new EvaluateNoReplyFragment();
        views4 = new EvaluateNoReplyFragment();
        views5 = new EvaluateReplyFragment();
        views6 =new SurveyListTabFragment();
        views7 = new EvaluateReplyFragment();
        views8 = new EvaluateNoReplyFragment();
        views9 = new EvaluateNoReplyFragment();;
        views10 = new EvaluateReplyFragment();
        views11=new EvaluateNoReplyFragment();
        views12 =new EvaluateNoReplyFragment();
        sumView = new ArrayList<>(Arrays.asList(new Fragment[]{views1,views2,
                views3,views4,views5,views6,views7,views8,views9,views10,views11,views12}));

        FragmentManager fm = getSupportFragmentManager();
        TabPagerAdapter madAdapter = new TabPagerAdapter(fm,sumView);
        vp_content_fragment.setAdapter(madAdapter);
        TabLayoutSupport.setupWithViewPager(tab_layout_title,vp_content_fragment,madAdapter);//将TabLayout和ViewPager关联起来
        tab_layout_title.setTabsFromPagerAdapter(madAdapter);//给Tabs设置适配器
    }

    class TabPagerAdapter extends FragmentStatePagerAdapter implements TabLayoutSupport.ViewPagerTabLayoutAdapter {
       private List<Fragment> fragments;

        LinkedHashMap<Integer,Fragment> mFragmentCache = new LinkedHashMap<>();

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public TabPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragmentCache.containsKey(position)?mFragmentCache.get(position):
                    fragments.get(position);

            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            MyFragment f = (MyFragment) super.instantiateItem(container, position);
//            String title = mList.get(position);
//            f.setTitle(title);
//            return f;
            return super.instantiateItem(container, position);
        }

        @Override
        public Parcelable saveState() {
            return super.saveState();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public String getPageTitle(int position) {
            return tab_title.get(position);
        }

        @Override
        public int getItemCount() {
            return tab_title.size();
        }
    }

}
