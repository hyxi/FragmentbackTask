package com.example.user.fragmentbacktask.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huangyuxi on 2019/3/15
 * Title: fragment 列表
 */
public class ListFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ListFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
