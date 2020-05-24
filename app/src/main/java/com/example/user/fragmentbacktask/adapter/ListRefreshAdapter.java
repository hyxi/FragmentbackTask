package com.example.user.fragmentbacktask.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;
import com.example.user.fragmentbacktask.entity.ListInfo;
import com.example.user.fragmentbacktask.entity.MoiveBean;
import com.example.user.fragmentbacktask.utils.ScreenUtils;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.view.AutoScrollViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首条view为广告listview
 */
public class ListRefreshAdapter extends BaseAdapter {

    private Context mContext;
    private List<MoiveBean> dataList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private static final int TYPE_AD = 0;
    private static final int TYPE_NORMAL = 1;
    private WeakReference<LinearLayout> ll_masks;
    private WeakReference<AutoScrollViewPager> viewPager;
    private boolean isAddListener = false;//控制setData()方法执行

    private ArrayList<String> adlist;

    public ListRefreshAdapter(Context mContext) {
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    public void resetData(ListInfo newData){
        adlist = newData.getAdlist();
        dataList.clear();
        dataList.addAll(newData.getDataList());
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return null;
        }
        return dataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_AD : TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolderHeader holderAd = null;
        ViewHolder holder1 = null;
        switch (type) {
            case TYPE_AD:
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.list_item_ad, null);
                    holderAd = new ViewHolderHeader();
                    convertView.setTag(holderAd);
                } else {
                    holderAd = (ViewHolderHeader) convertView.getTag();
                }
                holderAd.vp_turn_ad = (AutoScrollViewPager) convertView.findViewById(R.id.vp_turn_ad);
                holderAd.ll_ad_mark = (LinearLayout) convertView.findViewById(R.id.ll_ad_mark);
                ll_masks = new WeakReference<>(holderAd.ll_ad_mark);
                viewPager = new WeakReference<>(holderAd.vp_turn_ad);
                setAdData();
                break;
            case TYPE_NORMAL:
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.list_item_main, null);
                    holder1 = new ViewHolder();
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder) convertView.getTag();
                }

                holder1.iv_poster = (ImageView) convertView.findViewById(R.id.iv_poster);
                holder1.tv_movie_title = (TextView) convertView.findViewById(R.id.tv_movie_title);
                holder1.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

                String url = dataList.get(position - 1).imgurl;
                setImg(holder1.iv_poster, url);
                holder1.tv_movie_title.setText(dataList.get(position - 1).title);
                holder1.tv_content.setText(dataList.get(position - 1).content);
                break;
        }
        return convertView;
    }

    private void setAdData() {
        isAddListener = true;

        final ArrayList<String> list = adlist;

        if(adlist != null) {
            initImg(list.size());
            viewPager.get().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (flagNum != position) {
                        Log.i("onPageSelected", position + "");
                        flagNum = position;
                        changePosition(position % list.size());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            viewPager.get().getFilterTouchesWhenObscured();

            //设置viewpager自动滑动时间
            viewPager.get().setInterval(3000);
            viewPager.get().startAutoScroll(3000);
            viewPager.get().setScrollDurationFactor(2);
            TurnAdAdapter adapter = new TurnAdAdapter(mContext, list);
            viewPager.get().setAdapter(adapter);
            viewPager.get().setCurrentItem(0);
        }

    }

    public void setAdAutoScroll(boolean isAutoScroll) {
        if (viewPager != null) {
            if (isAutoScroll) {
                viewPager.get().startAutoScroll();
            } else {
                viewPager.get().stopAutoScroll();
            }
        }
    }

    private void initImg(int num) {
        ll_masks.get().removeAllViews();
        if (num != 1) {
            for (int i = 0; i < num; i++) {
                ImageView img = new ImageView(mContext);
                img.setImageResource(R.mipmap.ad_switcher_btn);
                if (ScreenUtils.screenWidth <= 480) {
                    img.setPadding(10, 0, 0, 0);
                } else {
                    img.setPadding(25, 0, 0, 0);
                }
                ll_masks.get().addView(img);
            }
            changePosition(0);
        }
    }

    private ImageView currentImg;// 当前选中的小圆点
    private int flagNum = 0;

    private void changePosition(int position) {
        if (currentImg != null)
            currentImg.setImageResource(R.mipmap.ad_switcher_btn);

        currentImg = (ImageView) ll_masks.get().getChildAt(position);
        if (currentImg == null)
            return;
        currentImg.setImageResource(R.mipmap.ad_switcher_btn_selected);
    }

    private void setImg(ImageView imageView, String imgUrl) {
        if (!StringUtils.isNullOrEmpty(imgUrl)) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.img_cover);
            Glide.with(TestApplication.getSelf()).load(Uri.parse(imgUrl))
                    .apply(requestOptions)
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    private class ViewHolderHeader {
        AutoScrollViewPager vp_turn_ad;
        LinearLayout ll_ad_mark;
    }

    private class ViewHolder {
        ImageView iv_poster;
        TextView tv_movie_title, tv_content;
    }

}
