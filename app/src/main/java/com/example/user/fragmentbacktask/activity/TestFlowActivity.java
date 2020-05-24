package com.example.user.fragmentbacktask.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.entity.ADInfo;
import com.example.user.fragmentbacktask.view.CycleViewPager;

import java.util.ArrayList;
import java.util.List;

//轮播广告
public class TestFlowActivity extends AppCompatActivity {

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    String[] images = { "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private Button btn_stop_turn;

    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg"
           };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flow);
        initViews();
        initialize();
        //overridePendingTransition();
    }

    private void initViews() {
        btn_stop_turn = (Button) findViewById(R.id.btn_stop_turn);
        btn_stop_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cycleViewPager.setAutoScroll(false);
            }
        });

    }

    private void initialize() {

        cycleViewPager = (CycleViewPager)getFragmentManager()
                .findFragmentById(R.id.fragmentr_content);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);

        }

    // 设置循环，在调用setData方法前调用
    cycleViewPager.setCycle(true);

    // 在加载数据前设置是否循环
    cycleViewPager.setData(infos, mAdCycleViewListener);
    //设置轮播
    cycleViewPager.setAutoScroll(true);

    // 设置轮播时间，默认5000ms
    cycleViewPager.setTime(2000);
}

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                Toast.makeText(TestFlowActivity.this,
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };
}
