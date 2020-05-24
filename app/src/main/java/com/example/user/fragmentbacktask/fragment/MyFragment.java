package com.example.user.fragmentbacktask.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.fragmentbacktask.chart.ChartActivity;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.RxjavaActivity;
import com.example.user.fragmentbacktask.adapter.ListFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/1/11.
 */
public class MyFragment extends Fragment {

    private String TAG = "MyFragment";

    private Context mContext;
    private TextView tv_message;
    private ViewPager vp_list;

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
        View view = inflater.inflate(R.layout.fragment_my_ll, null);
        mContext = getContext();

        vp_list = view.findViewById(R.id.vp_list);
        Button btn_rxjava = view.findViewById(R.id.btn_rxjava);
        btn_rxjava.setOnClickListener(v -> {
            startActivity(new Intent(mContext, RxjavaActivity.class));
        });
        view.findViewById(R.id.btn_chart).setOnClickListener(v -> {
            startActivity(new Intent(mContext, ChartActivity.class));
        });
        view.findViewById(R.id.btn_list).setOnClickListener(v -> {
            startActivity(new Intent(mContext, IndexPositionActivity.class));
        });

        initData();
        return view;
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("name", "first");
        fragments.add(Fragment.instantiate(mContext, EvaluateReplyFragment.class.getName(), bundle));
        fragments.add(Fragment.instantiate(mContext, EvaluateNoReplyFragment.class.getName()));
        bundle.putString("name", "second");
        fragments.add(Fragment.instantiate(mContext, EvaluateThirdReplyFragment.class.getName(), bundle));
        ListFragmentAdapter mFragmentAdapter = new ListFragmentAdapter(getChildFragmentManager(), fragments);
        vp_list.setAdapter(mFragmentAdapter);
    }

    private static int CODE_REQUEST = 1000;

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on onResume");
        requestPermissions();

        ActivityCompat.requestPermissions(getActivity() ,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (requestCode== CODE_REQUEST) {
                Log.i(TAG, "on granted");
            }
        } else {
            Log.i(TAG, "on refuesed");
        }
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "on onHiddenChanged//" + hidden);
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
