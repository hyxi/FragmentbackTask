package com.example.user.fragmentbacktask.navigation;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by huangyuxi on 2020-05-24
 */
public class NavigationImpl implements INavigation, KeyEvent.Callback {

    private FragmentManager mFragmentManager;
    private int mContainerViewId;

    private PauseHandler mPauseHandler;
    private OnBackResultListener mOnBackResultListener;

    private INavigationListener mNavigationListener;

    public NavigationImpl(FragmentManager fragmentManager, int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;

        mPauseHandler = new PauseHandler(Looper.getMainLooper(), fragmentManager);
    }


    public void setOnBackResultListenr(OnBackResultListener onBackResultListener) {
        this.mOnBackResultListener = onBackResultListener;
    }

    public void setNavigationListener(INavigationListener navigationListener) {
        this.mNavigationListener = navigationListener;
    }

    @Override
    public void transition(BusinessContext context, Intent intent) {
       Fragment currentFragment = getCurrentFragment(mFragmentManager);
       if (currentFragment == null) {
           transition(context, intent, TransactionAnimation.DEFAULT);
       } else {
           TransactionAnimation transactionAnimation = new TransactionAnimation();
           transition(context, intent, transactionAnimation);
       }
    }

    @Override
    public void transition(BusinessContext context, Intent intent, TransactionAnimation tranAnim) {
        Fragment page = matchPage(context, intent);
        if (page != null) {
            startPage(intent, page, tranAnim);
        } else {
            startActivity(context, intent, tranAnim);
        }
    }

    private void startActivity(BusinessContext businessContext, Intent intent, TransactionAnimation tranAnim) {
        ComponentName componentName = intent.getComponent();
        String name = componentName.getClassName();
        Context context = businessContext.getContext();
        boolean isActivity = context instanceof Activity;
        if (!isActivity) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
        if (isActivity) {
            int enterAnim = tranAnim != null && tranAnim.enter > 0 ? tranAnim.enter : TransactionAnimation.DEFAULT.enter;
            int exitAnim = tranAnim != null && tranAnim.exit > 0 ? tranAnim.exit : TransactionAnimation.DEFAULT.exit;
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    @Override
    public void startPage(Intent intent, Fragment page) {
        startPage(intent, page, null);
    }

    @Override
    public void startPage(Intent intent, Fragment page, TransactionAnimation tranAnim) {
        if (page != null && !page.isAdded()) {
            TransactionAnimation animation = tranAnim != null ? tranAnim : TransactionAnimation.DEFAULT;
            transitionImpl(mContainerViewId, page, intent, tranAnim);
        }
    }

    private Fragment matchPage(BusinessContext context, Intent intent) {
        ComponentName componentName = intent.getComponent();
        if (componentName != null) {
            String componentClassName = componentName.getClassName();

            try {
                Class pageClass = Class.forName(componentClassName);
                boolean isAssignable = Fragment.class.isAssignableFrom(pageClass);
                if (isAssignable) {
                    Fragment page = (Fragment) pageClass.newInstance();
                    if (page instanceof IComponent) {
                        ((IComponent) page).setBusinessContext(context);
                    }
                    Bundle bundle = intent.getExtras();
                    bundle = bundle == null ? new Bundle() : bundle;
                    Bundle pageBundle = page.getArguments();
                    if (pageBundle == null) {
                        page.setArguments(bundle);
                    } else {
                        pageBundle.putAll(bundle);
                    }
                    return page;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void transitionImpl(int containerViewId, Fragment page, Intent intent, TransactionAnimation animation) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (animation != null) {
            transaction.setCustomAnimations(animation.enter, animation.exit,
                    animation.popEnter, animation.popExit);
        }
        Fragment currentFragment = getCurrentFragment(mFragmentManager);

        String fragmentName = getFragmentName(page);
        boolean needClearFragmentTask = true;
        boolean addAction = false;
        boolean addBackStack = true;
        if (intent != null) {
            needClearFragmentTask = intent.getBooleanExtra(Navigation.BUNDLE_KEY_STACK_CLEAR, false);
            addAction = intent.getBooleanExtra(Navigation.BUNDLE_KEY_TRANSACTION_ADD, false);
            addBackStack = intent.getBooleanExtra(Navigation.BUNDLE_KEY_STACK_ADD, true);
        }

        if (needClearFragmentTask) {
            popBackClearStack(null);
        }
        if (addAction) {
            transaction.add(containerViewId, page, fragmentName);
            if (currentFragment != null) {
                FragmentTransaction hideTransaction = mFragmentManager.beginTransaction();
                hideTransaction.hide(currentFragment);
                hideTransaction.commitAllowingStateLoss();
            }
        } else {
            transaction.replace(containerViewId, page, fragmentName);
        }
        if (addBackStack) {
            transaction.addToBackStack(fragmentName);
        }

        if (currentFragment != null && mNavigationListener != null) {
            mNavigationListener.preLeaveHome();
        }

        safePost(() -> {
            Fragment topFragment = getCurrentFragment(mFragmentManager);
            if (topFragment != null && NavigationImpl.this.mNavigationListener != null) {
                NavigationImpl.this.mNavigationListener.onLeaveHome();
            }
            transaction.commitAllowingStateLoss();
        });

    }

    public void onResume() {
        mPauseHandler.resume();
    }

    public void onPause() {
        mPauseHandler.pause();
    }


    @Override
    public void popBackStack() {
        popBackStack(null);
    }

    public void popBackStack(Bundle bundle) {
        safePost(() -> {
            onPopBackStackResult(null, 0, bundle);
            mFragmentManager.popBackStack();
        });
    }

    public void popBackStack(int flag) {
        popBackStack(flag, null);
    }

    public void popBackStack(int flag, Bundle bundle) {
        switch (flag) {
            case Navigation.FLAG_CLEAR_TOP:
                popBackOnRoot(bundle);
                break;
            case Navigation.FLAG_CLEAR_TASK:
                popBackClearStack(bundle);
                break;
            case Navigation.FLAG_FINISH_ACTIVITY:
                popBackFinishActivity();
                break;
        }
    }

    private void popBackClearStack(Bundle bundle) {
        int count = mFragmentManager.getBackStackEntryCount();
        if (count > 0) {
            String rootName = mFragmentManager.getBackStackEntryAt(0).getName();
            popBackStack(rootName, 1, bundle);
        }
    }

    private void popBackFinishActivity() {
        Fragment fragment = getCurrentFragment(mFragmentManager);
        FragmentActivity activity = fragment.getActivity();
        if (null != activity) {
            activity.finish();
        }
    }

    private void popBackOnRoot(Bundle bundle) {
        int count = mFragmentManager.getBackStackEntryCount();
        if (count <= 1) {
            Logger.d("current is root Page");
        } else {
            String name = mFragmentManager.getBackStackEntryAt(0).getName();
            popBackStack(name, 0, bundle);
        }
    }

    private void popBackStack(String name, int flag, Bundle bundle) {
        safePost(() -> {
            if (null != name && !NavigationImpl.this.isFragmentInStack(name)) {
                NavigationImpl.this.popBackOnRoot(bundle);
            } else {
                onPopBackStackResult(name, flag, bundle);
                mFragmentManager.popBackStack(name, flag);
            }
        });
    }

    private void onPopBackStackResult(String name, int flag, Bundle bundle) {
        boolean onBackToHome = false;
        Fragment fragment = null;
        if (mFragmentManager.getBackStackEntryCount() == 1) {
            onBackToHome = true;
        } else if (TextUtils.isEmpty(name)) {
            fragment = getLastIndexFragment(mFragmentManager, 2);
        } else {
            int count = mFragmentManager.getBackStackEntryCount();
            for (int i = count - 1; i >= 0; --i) {
                FragmentManager.BackStackEntry stackEntry = mFragmentManager.getBackStackEntryAt(i);
                if (name.equals(stackEntry.getName())) {
                    if (flag == 1) {
                        if (i == 1) {
                            stackEntry = mFragmentManager.getBackStackEntryAt(i - 1);
                            fragment = mFragmentManager.findFragmentByTag(stackEntry.getName());
                        }
                    }
                } else {
                    fragment = mFragmentManager.findFragmentByTag(name);
                }
            }
        }
        if (fragment != null) {
            Bundle arguments = fragment.getArguments();
            if (arguments != null && bundle != null) {
                arguments.putAll(bundle);
            }
        } else if (onBackToHome && mOnBackResultListener != null) {
            mOnBackResultListener.onPopBackToHome(bundle);
        }
    }

    private void safePost(Runnable runnable) {
        Message transitionMsg = Message.obtain(mPauseHandler, 0, runnable);
        mPauseHandler.sendMessage(transitionMsg);
    }

    private boolean isFragmentInStack(String name) {
        if (mFragmentManager.getBackStackEntryCount() > 0
                && !TextUtils.isEmpty(name)) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                FragmentManager.BackStackEntry backStackEntry = mFragmentManager.getBackStackEntryAt(i);
                if (name.equals(backStackEntry.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Fragment getCurrentFragment(FragmentManager fragmentManager) {
        return getLastIndexFragment(fragmentManager, 1);
    }

    public Fragment getIndexFragment(int index) {
        return getLastIndexFragment(mFragmentManager, index);
    }

    private Fragment getLastIndexFragment(FragmentManager fragmentManager, int index) {
        int backEntryCount = fragmentManager.getBackStackEntryCount();
        if (backEntryCount <= index - 1) {
            return null;
        }
        FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(backEntryCount - index);
        String fragmentTag = backStackEntry.getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    @Override
    public String getFragmentName(@NonNull Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        return bundle != null && bundle.containsKey("BUNDLE_KEY_FRAGMENT_NAME") ? bundle.getString("BUNDLE_KEY_FRAGMENT_NAME")
                : fragment.getClass().getName() + "@" + System.identityHashCode(fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment = getCurrentFragment(mFragmentManager);
        if (fragment instanceof KeyEvent.Callback) {
            KeyEvent.Callback callback = (KeyEvent.Callback) fragment;
            return callback.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Fragment fragment = getCurrentFragment(mFragmentManager);
        if (fragment instanceof KeyEvent.Callback) {
            KeyEvent.Callback callback = (KeyEvent.Callback) fragment;
            return callback.onKeyLongPress(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Fragment fragment = getCurrentFragment(mFragmentManager);
        if (fragment instanceof KeyEvent.Callback) {
            KeyEvent.Callback callback = (KeyEvent.Callback) fragment;
            return callback.onKeyUp(keyCode, event);
        }
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        Fragment fragment = getCurrentFragment(mFragmentManager);
        if (fragment instanceof KeyEvent.Callback) {
            KeyEvent.Callback callback = (KeyEvent.Callback) fragment;
            return callback.onKeyMultiple(keyCode, count, event);
        }
        return false;
    }

    public static class PauseHandler extends Handler {

        private final Vector<Message> mBuffer = new Vector<>();
        private volatile boolean mIsPaused;

        private FragmentManager fragmentManager;

        PauseHandler(Looper looper, FragmentManager manager) {
            super(looper);
            fragmentManager = manager;
        }

        final void resume() {
            mIsPaused = false;
            while ((mBuffer.size() > 0) && mIsPaused) {
                final List<Message> messages = new ArrayList<>(mBuffer);
                mBuffer.clear();

                post(() -> {
                    if (!mIsPaused) {
                        for (Message message : messages) {
                            if (!mIsPaused) {
                                processMessage(message);
                                message.recycle();
                            } else {
                                mBuffer.add(message);
                            }
                        }
                    } else {
                        mBuffer.addAll(messages);
                    }
                });
            }
        }

        final void pause() {
            mIsPaused = true;
        }

        void processMessage(Message message) {
            Runnable runnable = (Runnable) message.obj;
            runnable.run();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mIsPaused) {
                Message msgCopy = Message.obtain(msg);
                mBuffer.add(msgCopy);
            } else {
                processMessage(msg);
            }
        }
    }

}
