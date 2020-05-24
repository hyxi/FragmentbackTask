package com.example.user.fragmentbacktask;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.user.fragmentbacktask.fragment.EvaluateListTabFragment;
import com.example.user.fragmentbacktask.fragment.MyFragment;
import com.example.user.fragmentbacktask.fragment.SurveyListTabFragment;
import com.example.user.fragmentbacktask.utils.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rbMain;
    private RadioButton rbMy;
    private TextView mTvPop;

    private int mCurrentTab = -1;
    private int mLastTab;
    private RadioButton rbSurvey;

    public static class TAB {
        public static final int TAB_MAIN = 0;
        public static final int TAB_MY = 1;
        public static final int TAB_SURVEY = 2;
    }

    private SparseArray<String> fragmentArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        if (fragmentArray.size() == 0) {
            fragmentArray.put(TAB.TAB_MAIN, EvaluateListTabFragment.class.getName());
            fragmentArray.put(TAB.TAB_MY, MyFragment.class.getName());
            fragmentArray.put(TAB.TAB_SURVEY, SurveyListTabFragment.class.getName());
        }
        initView();


        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    private void initView() {
        rbMain = (RadioButton) findViewById(R.id.rbMain);
        rbMy = (RadioButton) findViewById(R.id.rbMy);
        rbSurvey = (RadioButton) findViewById(R.id.rbSurvey);

        rbSurvey.setOnClickListener(this);
        rbMain.setOnClickListener(this);
        rbMy.setOnClickListener(this);
        setCurrentTab(TAB.TAB_MAIN);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventListener(MessageEvent messageEvent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbMain:
                setCurrentTab(TAB.TAB_MAIN);
                break;
            case R.id.rbMy:
                setCurrentTab(TAB.TAB_MY);
                break;
            case R.id.rbSurvey:
                setCurrentTab(TAB.TAB_SURVEY);
                break;
        }
    }

    public void setCurrentTab(int tab) {
        if (mCurrentTab == tab) {
            return;
        }
        mCurrentTab = tab;
        replaceFragment(tab);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("permission", "onRequestPermissionsResult");
    }

    private void replaceFragment(int tab) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment lastFragment = fm.findFragmentByTag(fragmentArray.get(mLastTab));
        String currentTag = fragmentArray.get(tab);
        Fragment currentFragment = fm.findFragmentByTag(currentTag);
        if (lastFragment != null) {
            ft.hide(lastFragment);
        }

        if (currentFragment == null) {
            currentFragment = Fragment.instantiate(this, currentTag);
            ft.add(R.id.container, currentFragment, currentTag);
            if (mCurrentTab != TAB.TAB_MAIN) {
                ft.addToBackStack(null);
            }
        } else {
            ft.show(currentFragment);
        }
        ft.commit();
        mLastTab = tab;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
