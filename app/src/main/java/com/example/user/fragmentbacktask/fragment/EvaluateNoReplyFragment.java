package com.example.user.fragmentbacktask.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

public class EvaluateNoReplyFragment extends Fragment {

    public String TAG="EvaluateNoReplyFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "on onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "on onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate_no_replay,null);
        TextView tv_noreply = (TextView) view.findViewById(R.id.tv_noreply);
//        Bundle bundle = getArguments();
//        String args = bundle.getString("exmapleArgs");
//        tv_noreply.setText(args);

        initAnimation();
        Log.i(TAG, "on onCreateView");
        return view;
    }

    private void initAnimation() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "on onStop");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "on setUserVisibleHint//" + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "on onHiddenChanged//" + hidden);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG,"on onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.i(TAG,"on detach");
        super.onDetach();
    }
}
