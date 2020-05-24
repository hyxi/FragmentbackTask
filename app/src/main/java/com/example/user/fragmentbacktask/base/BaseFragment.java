package com.example.user.fragmentbacktask.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View containerView = inflater.inflate(layoutId(),
                container, false);
        ButterKnife.bind(this, containerView);
        mContext = getActivity();
        initCreateView(inflater, container, savedInstanceState);
        return containerView;
    }

    public abstract int layoutId();

    public abstract void initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
