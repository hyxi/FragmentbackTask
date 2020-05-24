package com.example.user.fragmentbacktask.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.TestListAdapter;
import com.example.user.fragmentbacktask.entity.CommentList;
import com.example.user.fragmentbacktask.fragment.ChatBottomFragment;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


public class ChatInputBoardActivity extends FragmentActivity implements View.OnClickListener{

    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";

    private ImageView iv_add, iv_voice,iv_emotion;
    private ListView lv_test;
    private Context mContext;
    private RelativeLayout rl_b_container;
    private LinearLayout ll_h_bottom;
    private LinearLayout ll_bottom;
    private boolean isShow = false;//底部弹窗是否显示
    private SharedPreferences sp;

    private EditText et_keywored;
    int softkeyBoardHeight;
    int windowHeight;

    private Logger logger = Logger.getLogger("chat");


    private InputMethodManager mInputManager;//软键盘管理类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_test_list);
        initView();
        setData();
        mContext = this;

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        windowHeight = wm.getDefaultDisplay().getHeight();

        //设置软件盘的模式：SOFT_INPUT_ADJUST_RESIZE  这个属性表示Activity的主窗口总是会被调整大小，
        // 从而保证软键盘显示空间。从而方便我们计算软件盘的高度
        //这样会让屏幕整体上移
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mInputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        softkeyBoardHeight= sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT,0);
        softkeyBoardHeight = softkeyBoardHeight == 0 ? StringUtils.dp2px(250) + 2 : softkeyBoardHeight;
        addOnSoftKeyBoardVisibleListener(this, null);
    }

    private void initView() {
        rl_b_container = (RelativeLayout) findViewById(R.id.rl_b_container);

        lv_test = (ListView) findViewById(R.id.lv_test);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        iv_voice = (ImageView) findViewById(R.id.iv_voice);

        iv_emotion = (ImageView) findViewById(R.id.iv_emotion);

        et_keywored = (EditText) findViewById(R.id.et_keywored);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        iv_add.setOnClickListener(this);
        iv_voice.setOnClickListener(this);

        iv_emotion.setOnClickListener(this);

    }
    private void setData() {
        lv_test = (ListView) findViewById(R.id.lv_test);
        ArrayList<CommentList> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentList comment = new CommentList();
            comment.setContent("测试" + i);
            if(i%5 == 0){
                ArrayList<String> resp = new ArrayList<>(Arrays.asList("吃了这条蛆，姚明","你需要能量让自己变得更强大！"
                        ,"you need the energy to grow bigger！！！",
                        "以姚明的体型，一条蛆显然不够吃啊，贝爷请多准备一些吧。"));
                comment.setList(resp);
            }

            dataList.add(comment);
        }

        TestListAdapter adapter = new TestListAdapter(this, dataList);
        lv_test.setAdapter(adapter);
        lv_test.setSelection(dataList.size() - 1);


        replaceFragment();

        et_keywored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow && getSoftKeyBroadHeight() == 0) {
                    lockContentHeight();
                    rl_b_container.setVisibility(View.GONE);
                    isShow = false;
                    showSoftInput();
                    unlockContentHeightDelayed();
                }
            }
        });

        et_keywored.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (isShow && getSoftKeyBroadHeight() == 0) {
                        lockContentHeight();
                        rl_b_container.setVisibility(View.GONE);
                        isShow = false;
                        showSoftInput();
                        unlockContentHeightDelayed();
                    }
                }
            }
        });

//        adapter.setOnRepClickListener(new TestListAdapter.OnRespClickListener() {
//            @Override
//            public void onRespClick(int distance) {
//                showRespPopWindow(distance);
//            }
//        });
    }

    private void showRespPopWindow(int descend){

        int offset = windowHeight  - descend - softkeyBoardHeight-StringUtils.dp2px(55);
        lv_test.smoothScrollBy(-offset, 100);

        View view = LayoutInflater.from(this).inflate(R.layout.finance_respon_input_pop, null);
        PopupWindow popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText et_input = (EditText) view.findViewById(R.id.et_input);
        TextView tv_send = (TextView) view.findViewById(R.id.tv_send);

        String inputContent = et_input.getText().toString();
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("height","soft://"+getSoftKeyBroadHeight());
            }
        });

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setFocusable(true);
        int margin = StringUtils.dp2px(8);
        //防止虚拟软键盘被弹出菜单遮住
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //在底部显示
        popup.showAtLocation(lv_test, Gravity.BOTTOM, 0, 0);

        et_input.requestFocus();
        et_input.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(et_input, 0);
            }
        });
    }


    interface IKeyBoardVisibleListener{
        void onSoftKeyBoardVisible(boolean visible , int windowBottom);
    }
    boolean isVisibleForLast = false;

    public void addOnSoftKeyBoardVisibleListener(Activity activity, final IKeyBoardVisibleListener listener) {
        final View decorView = activity.getWindow().getDecorView();

        lv_test.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                logger.info(bottom+"bottom");
                // 高度改变
                if(isShow) {
//                    isShow = oldBottom > bottom;
                }
            }
        });


        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                //获得键盘高度
                int keyboardHeight = hight - displayHight;

//                if (isShow && getSoftKeyBroadHeight() == 0 && keyboardHeight > 0) {
//                    et_keywored.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            rl_b_container.setVisibility(View.GONE);
//                        }
//                    }, 200);
//                }
            }
        });
    }

    private void replaceFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentFragment = ChatBottomFragment.newInstance();
        ft.replace(R.id.rl_b_container, currentFragment);
        ft.attach(currentFragment);
        ft.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                //加载底部内容
                //Utils.hideSoftKeyBroad(mContext,et_keywored);
                showNewBottom();
                break;
            case R.id.iv_voice:
                int inputSoftHeight = getSoftKeyBroadHeight();
                Log.i("height", "intputhei://" + inputSoftHeight);
                break;
            case R.id.iv_emotion:
                int firstItemId = lv_test.getFirstVisiblePosition();
                Log.i("firstItemId",firstItemId+"");
                break;
        }
    }

    private void showNewBottom() {
        if(!isShow) {
            isShow = true;
            if (!isSoftInputShown()) {//如果键盘显示
                lockContentHeight();
                hideBottomLayout(false);
            }
            LinearLayout.LayoutParams flParams = (LinearLayout.LayoutParams) rl_b_container.getLayoutParams();
            int spKeyBoardHeight = sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT,0);
            flParams.height = spKeyBoardHeight == 0? softkeyBoardHeight: spKeyBoardHeight;
            rl_b_container.setLayoutParams(flParams);
        }else{
            if (isSoftInputShown()) {//软件盘不显示
                lockContentHeight();
                hideBottomLayout(true);
                unlockContentHeightDelayed();
            }else{
                lockContentHeight();
                hideBottomLayout(false);
                unlockContentHeightDelayed();
            }
        }
    }

    private void hideBottomLayout(boolean isHide){
        if(isHide){
            ((LinearLayout.LayoutParams) lv_test.getLayoutParams()).weight = 1.0F;
            isShow = false;
            LinearLayout.LayoutParams flParams = (LinearLayout.LayoutParams) rl_b_container.getLayoutParams();
            int height = flParams.height;
            ValueAnimator animator = ValueAnimator.ofInt(height, 0);
            animator.setTarget(rl_b_container);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    LinearLayout.LayoutParams flParams = (LinearLayout.LayoutParams) rl_b_container.getLayoutParams();
                    flParams.height = (int) animation.getAnimatedValue();
                    logger.info("chat_mag:" + flParams.height);
                    rl_b_container.setLayoutParams(flParams);
                }
            });
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator(1.2f));
            animator.start();
//           showSoftInput();
        }else{
            Utils.hideSoftKeyBroad(mContext, et_keywored);
            rl_b_container.setVisibility(View.VISIBLE);
        }
    }

    private void showSoftInput() {
        et_keywored.requestFocus();
        et_keywored.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(et_keywored, 0);
            }
        });

    }

    private boolean isSoftInputShown() {
        return getSoftKeyBroadHeight() == 0;
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        et_keywored.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) lv_test.getLayoutParams()).weight = 1.0F;
            }
        }, 200);
    }

    //锁定内容高度，防止跳闪
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lv_test.getLayoutParams();
        params.height = lv_test.getHeight();
        params.weight = 0.0F;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.showSoftKeyBroad(mContext, et_keywored);
        et_keywored.requestFocus();
    }

    private int getSoftKeyBroadHeight() {

        Rect r = new Rect();
        /*
        * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
        * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
        */
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }

        if (softInputHeight < 0) {
            // LogUtils.w("EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, softInputHeight).apply();
        }

        return softInputHeight;
    }


    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


}
