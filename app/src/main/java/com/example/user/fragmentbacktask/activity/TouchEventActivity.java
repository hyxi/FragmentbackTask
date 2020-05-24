package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.user.fragmentbacktask.BaseNewActivity;
import com.example.user.fragmentbacktask.MainActivity;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.TestApplication;
import com.example.user.fragmentbacktask.activity.test.TestRecyclerActivity;
import com.example.user.fragmentbacktask.adapter.TestStringAdapter;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.view.PageLoadingView;
import com.example.user.fragmentbacktask.view.autoViewpager.VPFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TouchEventActivity extends Activity implements View.OnClickListener {

    private Button btn_webview;
    private Button open_at;
    private Context mContext;

    private TestApplication mApp;
    private VPFrameLayout auto_Vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        mApp = TestApplication.getSelf();
        mContext = this;
        initView();
        registerListener();
    }

    private void registerListener() {
        open_at.setOnClickListener(this);
    }

    private void initView() {
        btn_webview = (Button) findViewById(R.id.btn_webview);
        open_at = (Button) findViewById(R.id.open_at);
        btn_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://luna.visualbusiness.cn/luna-web/common/merchant/dailyReportPage?id=32&title=&isWebView=true&isWebview=true";
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", "测试");
                startActivity(intent);
            }
        });

        TextView tv_snacker = (TextView) findViewById(R.id.tv_snacker);

        tv_snacker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnacker(findViewById(R.id.content),"snacker测试");
            }
        });

        findViewById(R.id.test_rv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TestRecyclerActivity.class));
            }
        });
    }

    private void showSnacker(View rootView,String msg){
        TSnackbar snackBar = TSnackbar
                .make(rootView, msg, TSnackbar.LENGTH_LONG);
        View view = snackBar.getView();
        view.setBackgroundResource(R.color.red_new);
        TextView tv = ((TextView) view.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text));
        tv.setTextColor(Color.WHITE);
        snackBar.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.open_at:
                //mApp.getUpdateManager().checkForUpDate();
                startActivity(new Intent(mContext, com.example.user.fragmentbacktask.activity.webview.MainActivity.class));
                break;
        }

    }
    
    protected void handleHeaderEvent() {
        Toast.makeText(this, "右侧按钮被点击！！！", Toast.LENGTH_SHORT).show();
    }
}
