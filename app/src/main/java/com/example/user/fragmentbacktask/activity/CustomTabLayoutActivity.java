package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.asyncinflate.AsyncInflateItem;
import com.example.user.fragmentbacktask.utils.asyncinflate.AsyncInflaterManager;
import com.example.user.fragmentbacktask.view.tablayout.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomTabLayoutActivity extends FragmentActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    boolean hasReplaceAllEmptyFragments;
    private List<Fragment> mFragmentList;
    private List<Fragment> mEmptyFragmentList;
    private List<Fragment> mDataFragmentList;
    private CustomPagerAdapter mTabsAdapter;
    private boolean hasReplacedAllEmptyFragments;
    private int mCurrentSelectedTab;
    private int mDefaultTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_tab_layout);
        LayoutInflater.from(this).inflate(R.layout.activity_custom_tab_layout, null, false);
        findView();
        setListener();
        SparseArray<String> map = new SparseArray<>();
        map.put(10, "sdfsdf");
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        arrayMap.put("a", "value");

    }

    private void findView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(this);
        mFragmentList = new ArrayList<>();
        mEmptyFragmentList = new ArrayList<>();
        mDataFragmentList = new ArrayList<>();
        mFragmentList.add(Fragment.instantiate(getApplicationContext(), CustomFragment.class.getName()));
        String[] titles = {"头条", "新闻", "热点", "体育", "娱乐", "科技", "订阅", "财经",
                "广州"};
        Context context = new MutableContextWrapper(getApplicationContext());
        AsyncInflaterManager asyncInflaterManager = AsyncInflaterManager.getInstance();
        for (int i = 0; i < titles.length - 1; i++) {
            Fragment emptyFragment = Fragment.instantiate(getApplicationContext(), EmptyPlaceHolderFragment.class.getName());
            mFragmentList.add(emptyFragment);
            mEmptyFragmentList.add(emptyFragment);
            Fragment fragment = Fragment.instantiate(getApplicationContext(), CustomFragment.class.getName());
            Bundle bundle = new Bundle();
            bundle.putString("title", titles[i + 1]);
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            mDataFragmentList.add(fragment);
            asyncInflaterManager.asyncInflate(context, new AsyncInflateItem("fragment" + i, R.layout.fragment_lazy_load_layout));
        }
        mTabsAdapter = new CustomPagerAdapter(
                getSupportFragmentManager(),titles);
        mViewPager.setAdapter(mTabsAdapter);
        mTabsAdapter.notifyDataSetChanged();
        for (int i = 0; i < mTabsAdapter.getCount(); i++) {
            String title = mTabsAdapter.getPageTitle(i).toString();
            mTabLayout.addTab(i, title);
        }
        mTabLayout.initStrip();
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(position);
                mCurrentSelectedTab = position;
                if (!hasReplacedAllEmptyFragments &&
                        mCurrentSelectedTab != mDefaultTab) {
                    replaceEmptyFragmentsIfNeed(mCurrentSelectedTab);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                mTabLayout.selectTab(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (!hasReplacedAllEmptyFragments &&
//                        mCurrentSelectedTab != mDefaultTab
//                        && state == 0) {
//                    replaceEmptyFragmentsIfNeed(mCurrentSelectedTab);
//                }
            }
        });
        mTabLayout.setOnSelectedCallBack(new TabLayout.OnSelectedCallBack() {
            @Override
            public void selected(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
    }

    class CustomPagerAdapter extends FragmentPagerAdapter {

        private String[] mTitles;

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public CustomPagerAdapter(@NonNull FragmentManager fm, String[] mTitles) {
            super(fm);
            this.mTitles = mTitles;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mFragmentList.get(position).hashCode();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            int index = mFragmentList.indexOf(object);
            if (index == -1) {
                return POSITION_NONE;
            } else {
                return mFragmentList.indexOf(object);
            }
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        void refreshFragments(List<Fragment> fragmentList) {
            mFragmentList = fragmentList;
            notifyDataSetChanged();
        }
    }

    private void replaceEmptyFragmentsIfNeed(int tabId) {
        if (hasReplaceAllEmptyFragments) {
            return;
        }
        int tabRealIndex = mEmptyFragmentList.indexOf(mFragmentList.get(tabId));
        if (tabRealIndex > -1) {
            if (Collections.replaceAll(mFragmentList, mEmptyFragmentList.get(tabRealIndex), mDataFragmentList.get(tabRealIndex))) {
                mTabsAdapter.refreshFragments(mFragmentList);
                boolean hasAllReplaced = true;
                for (Fragment fragment : mFragmentList) {
                    if (fragment instanceof EmptyPlaceHolderFragment) {
                        hasAllReplaced = false;
                        break;
                    }
                }
                if (hasAllReplaced) {
                    mEmptyFragmentList.clear(); //全部替换完成的话，释放引用
                }
                hasReplacedAllEmptyFragments = hasAllReplaced;
            }
        }
    }

    public static class EmptyPlaceHolderFragment extends Fragment {
    }

    public static class CustomFragment extends Fragment {
        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            View view = null;
            if (bundle != null) {
                String title = bundle.getString("title");
                int index = bundle.getInt("index", -1);
                Context context = new MutableContextWrapper(getActivity().getApplicationContext());
                view = AsyncInflaterManager.getInstance().getInflatedView(context, R.layout.fragment_lazy_load_layout,null
                        , "fragment" + index, LayoutInflater.from(context));
                TextView tvMessage = view.findViewById(R.id.tv_message);
                tvMessage.setText(title);
//                TextView tv = new TextView(getActivity());
//                tv.setText(title);
//                tv.setBackgroundColor(Color.argb((int) (Math.random() * 255), // alpha
//                        (int) (Math.random() * 255), // red
//                        (int) (Math.random() * 255), // green
//                        (int) (Math.random() * 255))); // blue
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextColor(Color.WHITE);
//                tv.setTextSize(20);
            }

            return view;
        }
    }
}


