package com.example.user.fragmentbacktask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.kotlin.model.User;
import com.example.user.fragmentbacktask.kotlin.view.CollapseActivity;
import com.example.user.fragmentbacktask.kotlin.view.ProjectListActivity;
import com.example.user.fragmentbacktask.utils.rxbus.EventMessage;
import com.example.user.fragmentbacktask.utils.rxbus.RxBus;
import com.example.user.fragmentbacktask.view.GeneralEditDialog;
import com.example.user.fragmentbacktask.view.marquee.SimpleMF;
import com.example.user.fragmentbacktask.view.marquee.SimpleMarqueeView;
import com.github.promeg.pinyinhelper.Pinyin;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.icon_typeface.Entypo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PinyinActivity extends BaseActivity {

    @BindView(R.id.et_input)
    EditText et_input;
    @BindView(R.id.tv_result)
    TextView tv_result;
    @BindView(R.id.btn_transform)
    Button btn_transform;
    @BindView(R.id.marquee_finance)
    SimpleMarqueeView marquee_finance;
    @BindView(R.id.tv_text_one)
    TextView tv_text_one;
    @BindView(R.id.tv_text_line)
    TextView tv_text_line;
    @BindView(R.id.show_dialog)
    Button show_dialog;
    @BindView(R.id.btn_new)
    Button btn_new;
    @BindView(R.id.iv_icon)
    ImageView icon;
    @BindView(R.id.btn_fullscreen)
    Button btnFull;

    private final List<String> datas = Arrays.asList("《赋得古原草送别》测试测试测试测试测试测试测测试测试测试测试测试测试测", "离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。", "好雨知时节，当春乃发生。随风潜入夜，润物细无声。野径云俱黑，江船火烛明。晓看云湿处，花重锦官城。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。", "测试测试测试测试测试测试测试测试测试测试测试");

    @Override
    public int provideLayoutId() {
        return R.layout.activity_pinyin;
    }

    @Override
    public void initCreate(Bundle savedInstanceState) {
        initView();
        initText();
    }

    private void initText() {
        RxBus.getInstance().post(new EventMessage("test", "a rxbus test"));

    }


    private void initView() {
        String iconString = Entypo.Icon.icon_weib.getName();
//        IconicsDrawable drawable = new IconicsDrawable(mContext, iconString)
//                .sizeDp(200)
//                .paddingDp(6);
//        icon.setImageDrawable(drawable);


        btn_transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et_input.getText().toString().trim();
                if (input.length() > 0) {
                    String firstWord = input.substring(0, 1);
                    if (firstWord.matches("[\\u4e00-\\u9fa5]")) {
                        String pinyin = Pinyin.toPinyin(firstWord.charAt(0));
                        tv_result.setText(pinyin.substring(0, 1));
                    } else {
                        tv_result.setText(firstWord);
                    }
                }
            }
        });

        show_dialog.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ProjectListActivity.class);
            User user = new User("阿呆", "123456");
            intent.putExtra("user",  user);
            intent.putExtra("string", "一个字符串");
            Bundle bundle = new Bundle();
            bundle.putString("extra", " a test");
            intent.putExtra("bundle", bundle);
            mContext.startActivity(intent);
        });

        btnFull.setOnClickListener(view -> {
          mContext.startActivity(new Intent(mContext, FullActivityActivity.class));
        });

        SimpleMF<String> simpleMF = new SimpleMF<>(mContext);
        simpleMF.setData(datas);
        marquee_finance.setMarqueeFactory(simpleMF);
        marquee_finance.startFlipping();
    }

    @OnClick({R.id.btn_new})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new:
                mContext.startActivity(new Intent(mContext, CollapseActivity.class));
                break;
        }
    }

}
