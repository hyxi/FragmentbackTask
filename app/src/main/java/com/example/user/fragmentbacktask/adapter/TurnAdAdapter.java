package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;

import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by huangyx on 2016/5/15.
 */
//图片轮播adapter
public class TurnAdAdapter extends PagerAdapter {

    private ArrayList<String> adList;
    private Context mContext;

    public TurnAdAdapter(Context mContext, ArrayList<String> adList) {
        this.mContext = mContext;
        this.adList = adList;
    }

    @Override
    public int getCount() {
        return Byte.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item_ad, null);
        ImageView iv_ad = (ImageView) view.findViewById(R.id.iv_ad);
        Glide.with(TestApplication.getSelf()).load(adList.get(position % adList.size())).into(iv_ad);
        container.addView(view);
        return view;
    }
}
