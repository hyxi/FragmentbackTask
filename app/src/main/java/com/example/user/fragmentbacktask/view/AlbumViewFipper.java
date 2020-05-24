package com.example.user.fragmentbacktask.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.PictureInfo;
import com.example.user.fragmentbacktask.utils.ImageUtils;

import java.util.List;

/**
 * Created by hyxi on 2016/1/21.
 */
public class AlbumViewFipper extends ViewFlipper {

    private List<PictureInfo> mList;
    private static final int MAX_CHILD_COUNT = 5;
    private static final int HALF_MAX_CHILD_COUNT = 2;
    private Context mContext;
    private ImageView picImageView;

    private LayoutInflater layoutInflater;
    private ImageView pic_test_view;
    private LinearLayout.LayoutParams lp;
    private int footPosition, headerPosition, currentPostion;//首位child在list中的位置,第三个标示显示的child当前位置

    public AlbumViewFipper(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AlbumViewFipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void setValues(List<PictureInfo> list, int Position) {//这里的position从0开始
        mList = list;
        if (mList.size() > MAX_CHILD_COUNT) {
            headerPosition = Position - HALF_MAX_CHILD_COUNT;
            footPosition = Position + HALF_MAX_CHILD_COUNT;
            if (headerPosition < 0) {
                footPosition += Math.abs(headerPosition);
                headerPosition = 0;
            }
            if (footPosition > mList.size() - 1) {
                headerPosition -= (footPosition - mList.size() + 1);
                footPosition = mList.size() - 1;
            }
            for (int i = 0; i < MAX_CHILD_COUNT; i++) {
                addSetImageView(headerPosition + i, i);
            }

        } else {
            for (int i = 0; i < mList.size(); i++) {
                addSetImageView(headerPosition + i, i);
            }
        }
        setDisplayedChild(Position - headerPosition);
        currentPostion = Position;
    }

    /**
     * @param listIndex 数据在list中的位置
     * @param index     添加的child位置
     */
    private void addSetImageView(int listIndex, int index) {
        View view = layoutInflater.inflate(R.layout.layout_picture_info, null);
        pic_test_view = (ImageView) view.findViewById(R.id.pic_test_view);
        pic_test_view.setImageBitmap(ImageUtils.getImageFromSD(mList.get(listIndex).imgUrl, 600, 800));
        this.addView(view, index, lp);
    }

    @Override
    public void showNext() {
        currentPostion++;
        if (mList.size() > MAX_CHILD_COUNT) {
            if (footPosition >= mList.size() - 1) {
                footPosition = mList.size() - 1;
            } else {
                if (currentPostion > HALF_MAX_CHILD_COUNT) {//为了解决在header=0的时候移动
                    footPosition++;
                    headerPosition++;
                    addSetImageView(footPosition, getChildCount());
                    removeViewAt(0);
                    setDisplayedChild(getDisplayedChild());
                    return;
                }
            }
        }
        super.showNext();
    }

    @Override
    public void showPrevious() {
        currentPostion--;
        if (mList.size() > MAX_CHILD_COUNT) {
            if (headerPosition <= 0) {
                headerPosition = 0;
            } else {
                if (currentPostion < mList.size() - 1 - HALF_MAX_CHILD_COUNT) {
                    headerPosition--;
                    footPosition--;
                    addSetImageView(headerPosition, 0);
                    removeViewAt(getChildCount() - 1);
                    Log.i("tag", "count " + getDisplayedChild());
                    setDisplayedChild(getDisplayedChild() - 1);
                    return;
                }
            }
        }
        super.showPrevious();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        picImageView = (ImageView) getChildAt(getDisplayedChild()).findViewById(R.id.pic_test_view);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null == picImageView) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (null == picImageView)
                    return true;
        }
        return false;
    }
}
