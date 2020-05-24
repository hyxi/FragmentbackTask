package com.example.user.fragmentbacktask.view.bottompickerdialog.costompicker;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.bottompickerdialog.WindowUtil;

import java.util.List;

/**
 * 自定义的选择器弹出框
 */
public class CustomPickerDialog implements OnClickListener, CustomPicker.OnPickListener {
    Dialog timeDialog;
    private Context context;
    private Display display;
    private CustomPicker tPicker;
    private int[] selectedIndexs;
    private CustomPicker.OnPickListener listener;

    public CustomPickerDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CustomPickerDialog build(PickerListener pl, List<PickerData> datas) {
        this.pl = pl;
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_custompick_dialog, null);
        timeDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        timeDialog.setContentView(view);

        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        tPicker = (CustomPicker) view.findViewById(R.id.time_picker);
        tPicker.setData(datas);
        tPicker.setmOnPickListener(this);
        //保证出现5行
        ViewGroup.LayoutParams lp1 = tPicker.getLayoutParams();
        lp1.height = WindowUtil.sp2px(context, context.getResources().getDimension(R.dimen.font_size_s_large)) * 11;
        tPicker.setLayoutParams(lp1);
        if (defaultIndexs != null) {
            tPicker.setStartStatus(defaultIndexs);
        }
        cancle.setOnClickListener(this);
        confirm.setOnClickListener(this);
        Window dialogWindow = timeDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        dialogWindow.setLayout(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    private int[] defaultIndexs;

    /**
     * 设置初始的位置
     *
     * @param defaultIndexs
     * @return
     */
    public CustomPickerDialog setOriIndexs(int... defaultIndexs) {
        this.defaultIndexs = defaultIndexs;
        return this;
    }

    public void show() {
        if (timeDialog != null) {
            timeDialog.setCancelable(true);
            timeDialog.show();
        }
        if (tPicker != null) {
            selectedIndexs = new int[]{};
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                timeDialog.dismiss();
                break;
            case R.id.confirm:
                timeDialog.dismiss();
                if (null != pl) {
                    pl.onPick(selectedIndexs);
                }
                break;
        }
    }

    /**
     * dialog中确定按钮的回调
     */
    private PickerListener pl;

    @Override
    public void onPickIndexs(int... indexs) {
        selectedIndexs = indexs;
    }

    public interface PickerListener {
        public void onPick(int... indexs);
    }

}
