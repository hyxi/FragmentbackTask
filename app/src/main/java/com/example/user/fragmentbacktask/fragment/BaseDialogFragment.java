package com.example.user.fragmentbacktask.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.user.fragmentbacktask.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {
    public boolean isCenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.BottomDialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = isCenter ? Gravity.CENTER : Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow()
                .setAttributes(params);
    }
}
