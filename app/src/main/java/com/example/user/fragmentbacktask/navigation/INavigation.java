package com.example.user.fragmentbacktask.navigation;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

/**
 * Created by huangyuxi on 2020-05-24
 * Title:
 */
public interface INavigation extends Serializable {

    void transition(BusinessContext context, Intent intent);

    void transition(BusinessContext context, Intent intent, TransactionAnimation tranAnim);

    void popBackStack();

    void startPage(Intent intent, Fragment page);

    void startPage(Intent intent, Fragment page, TransactionAnimation tranAnim);

    String getFragmentName(@NonNull Fragment fragment);

    class TransactionAnimation {
        public static final TransactionAnimation DEFAULT = new TransactionAnimation(0, 0, 0, 0);

        int enter;
        int exit;
        int popEnter;
        int popExit;

        public TransactionAnimation() {
        }

        public TransactionAnimation(int enter, int exit, int popEnter, int popExit) {
            this.enter = enter;
            this.exit = exit;
            this.popEnter = popEnter;
            this.popExit = popExit;
        }
    }
}
