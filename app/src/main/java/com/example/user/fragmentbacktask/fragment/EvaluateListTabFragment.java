package com.example.user.fragmentbacktask.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.base.BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * huangyx
 */
public class EvaluateListTabFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = EvaluateListTabFragment.class.getSimpleName();
    @BindView(R.id.tv_tabline)
    TextView tvTabline;
    @BindView(R.id.rb_evaluate_left)
    RadioButton rbEvaluateLeft;
    @BindView(R.id.rb_evaluate_middle)
    RadioButton rbEvaluateMiddle;
    @BindView(R.id.rb_evaluate_right)
    RadioButton rbEvaluateRight;
    @BindView(R.id.rg_evaluate_list)
    RadioGroup rgEvaluateList;
    @BindView(R.id.vp_evaluate_content)
    ViewPager vpEvaluateContent;

    private List<Fragment> fragments;
    private int currentIndex;
    private int screenWidth;
    private float startPos = 0.0f;


    public static final class SUB_TAB {
        public static final int SUB_TAB_LEFT = 1;
        public static final int SUB_TAB_MIDDLE = 2;
        public static final int SUB_TAB_RIGHT = 3;
    }

    public static Fragment newInstance() {
        Fragment fragment = new EvaluateListTabFragment();
        return fragment;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_evaluate_list_ll;
    }

    @Override
    public void initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FrameLayout.LayoutParams rg_fl = (FrameLayout.LayoutParams) rgEvaluateList
                .getLayoutParams();
        screenWidth = rg_fl.width;
        initTabText();
        setListeners();
        setViewpager();
    }

    private void initTabText() {
//
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) tv_tabline
//                .getLayoutParams();
//        lp.width = screenWidth / 3;
//        tv_tabline.setLayoutParams(lp);
    }

    private void setListeners() {
        rgEvaluateList.setOnCheckedChangeListener(this);
        vpEvaluateContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //positionOffset 当前界面移动百分比
            //positionOffsetPixels 当前页面偏移的像素位置
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) tv_tabline.getLayoutParams();
//                if (currentIndex == 0 && position == 0) {// 0->1
//                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
//                            * (screenWidth / 3));
//                } else if (currentIndex == 1 && position == 0) {// 1->0
//                    lp.leftMargin = (int) (-(1 - positionOffset)
//                            * (screenWidth * 1.0 / 3) + currentIndex
//                            * (screenWidth / 3));
//
//                } else if (currentIndex == 1 && position == 1) { // 1->2
//                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
//                            * (screenWidth / 3));
//                } else if (currentIndex == 2 && position == 1) {// 2->1
//                    lp.leftMargin = (int) (-(1 - positionOffset)
//                            * (screenWidth * 1.0 / 3) + currentIndex
//                            * (screenWidth / 3));
//                } else if (currentIndex == 2 && position == 2) {
//                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
//                            * (screenWidth / 3));
//                }
//                if(position == 0){
//                    rb_evaluate_left.setAlpha(Math.abs(1-2*positionOffset));
//                    rb_evaluate_middle.setAlpha(Math.abs(1-2*positionOffset));
//                }else if(position == 1 || position == 2){
//                    rb_evaluate_middle.setAlpha(Math.abs(1-2*positionOffset));
//                    rb_evaluate_right.setAlpha(Math.abs(1-2*positionOffset));
//                }
//                tv_tabline.setLayoutParams(lp);

//                int[] location = new int[2];
//                tv_tabline.getLocationOnScreen(location);
//                int x = location[0];
//                int endx = (int) (x + screenWidth / 3f * positionOffset);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_tabline,"translationX",
//                        x, endx);
//                animator.start();

//                if(positionOffset > 0.99){
//                    endx = screenWidth*(position+1)/3;
//                }else{
//                    if (positionOffset != 0)
//                        endx = (int) (screenWidth / 3f * positionOffset);
//                    else{
//                       if()
//                    }
//                }
                float endx = screenWidth / 3f * (position + positionOffset);
                ObjectAnimator animator = ObjectAnimator.ofFloat(tvTabline, "translationX",
                        startPos, endx);
                animator.start();
                startPos = endx;
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbEvaluateLeft.setChecked(true);
                        break;
                    case 1:
                        rbEvaluateMiddle.setChecked(true);
                        break;
                    case 2:
                        rbEvaluateRight.setChecked(true);
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setViewpager() {
        fragments = new ArrayList<>();
        fragments.add(new EvaluateWholeFragment());
        fragments.add(new EvaluateNoReplyFragment());
        fragments.add(new EvaluateReplyFragment());
        FragmentManager fm = getChildFragmentManager();
        EvaluateListAdapter adapter = new EvaluateListAdapter(fm);
        vpEvaluateContent.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_evaluate_left:
                vpEvaluateContent.setCurrentItem(0, false);
                rbEvaluateLeft.setChecked(true);
                break;
            case R.id.rb_evaluate_middle:
                vpEvaluateContent.setCurrentItem(1, false);
                rbEvaluateMiddle.setChecked(true);
                break;
            case R.id.rb_evaluate_right:
                vpEvaluateContent.setCurrentItem(2, false);
                rbEvaluateRight.setChecked(true);
                break;
        }
    }

    class EvaluateListAdapter extends FragmentPagerAdapter {

        public EvaluateListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

        @Override
        public Fragment getItem(int postion) {
            return fragments.get(postion);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
