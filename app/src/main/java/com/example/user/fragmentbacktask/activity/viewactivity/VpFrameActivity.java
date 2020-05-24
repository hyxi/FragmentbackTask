package com.example.user.fragmentbacktask.activity.viewactivity;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.view.autoViewpager.VPFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class VpFrameActivity extends Activity {

    private VPFrameLayout auto_Vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_frame);
        initData();
    }

    private void initData() {
        String[] urls = getResources().getStringArray(R.array.url);
        ArrayList<String> tempList =  new ArrayList<>(Arrays.asList(urls));
        auto_Vp = (VPFrameLayout) findViewById(R.id.auto_viewpager);
        auto_Vp.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, StringUtils.dp2px(200f)));
        auto_Vp.setViews(tempList)
                .start();
    }
}
