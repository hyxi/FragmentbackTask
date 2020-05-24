package com.example.user.fragmentbacktask.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.ChatBottomAdapter;
import com.example.user.fragmentbacktask.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 聊天底部功能栏
 */
public class ChatBottomFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String TAG = ChatBottomFragment.class.getSimpleName();

    private GridView gv_sorts;
    private Context mContext;
    private ChatBottomAdapter adapter;

    public static Fragment newInstance(){
        return new ChatBottomFragment();
    }

    private int[] icons = new int[]{R.drawable.chat_pop_img,R.mipmap.chat_video_n,
            R.drawable.chat_pop_position,R.mipmap.chat_pop_live_n,R.mipmap.chat_pop_live_n};
    private ArrayList<String> functions = new ArrayList<>(
            Arrays.asList("图片","视频","位置","红包","直播看房"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_bottom, null);
        mContext = getActivity();

        initView(view);
        return view;
    }

    private void initView(View view) {
        gv_sorts = (GridView) view.findViewById(R.id.gv_sorts);
        adapter = new ChatBottomAdapter(mContext,functions,icons);
        gv_sorts.setAdapter(adapter);

        gv_sorts.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = functions.get(position);
        StringUtils.toast(mContext,"positon"+position+"://"+name);
    }



}
