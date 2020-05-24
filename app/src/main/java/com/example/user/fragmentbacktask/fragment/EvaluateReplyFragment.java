package com.example.user.fragmentbacktask.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

/**
 * Created by user on 2016/1/12.
 */
public class EvaluateReplyFragment extends Fragment {

    public String TAG="EvaluateReplyFragment";
    private TextView tv_content;

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
        View view = inflater.inflate(R.layout.fragment_evaluate_replay,container, false);
        if (getArguments() != null) {
            tv_content = view.findViewById(R.id.tv_content);
            String content = getArguments().getString("name");
            tv_content.setText(content);
        }

        Log.i(TAG, "on onCreateView");
        return view;
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
    public boolean getUserVisibleHint() {
        Log.i(TAG, "on getUserVisibleHint");
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "on setUserVisibleHint//" + isVisibleToUser);
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
