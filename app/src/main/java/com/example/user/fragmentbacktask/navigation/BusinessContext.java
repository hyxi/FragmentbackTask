package com.example.user.fragmentbacktask.navigation;

import android.content.Context;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by huangyuxi on 2020-05-24
 * Title:
 */
public class BusinessContext implements KeyEvent.Callback {

    private Context mContext;
    private NavigationImpl mNavigationImpl;

    public BusinessContext(FragmentActivity activity) {
        this.mContext = activity;

        mNavigationImpl = new NavigationImpl(activity.getSupportFragmentManager(), 0);
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public NavigationImpl getNavigation() {
        return mNavigationImpl;
    }

    public void setNavigation(NavigationImpl navigation) {
        this.mNavigationImpl = navigation;
    }

    public void onResume() {
        if (mNavigationImpl != null) {
            mNavigationImpl.onResume();
        }
    }

    public void onPause() {
        if (mNavigationImpl != null) {
            mNavigationImpl.onPause();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mNavigationImpl != null) {
           return mNavigationImpl.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (mNavigationImpl != null) {
            return mNavigationImpl.onKeyLongPress(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mNavigationImpl != null) {
            return mNavigationImpl.onKeyUp(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        if (mNavigationImpl != null) {
            return mNavigationImpl.onKeyMultiple(keyCode, count, event);
        }
        return false;
    }
}
