package com.example.user.fragmentbacktask.navigation;

/**
 * Created by huangyuxi on 2020-05-24
 */
public interface Navigation {
    int FLAG_CLEAR_TOP = 0;
    int FLAG_CLEAR_TASK = 1;
    int FLAG_FINISH_ACTIVITY = 2;

    String BUNDLE_KEY_STACK_CLEAR = "BUNDLE_KEY_STACK_CLEAR";
    String BUNDLE_KEY_STACK_ADD = "BUNDLE_KEY_STACK_ADD";
    String BUNDLE_KEY_TRANSACTION_ADD = "BUNDLE_KEY_TRANSACTION_ADD";
}
