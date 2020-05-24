package com.example.user.fragmentbacktask.view.bottompickerdialog.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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

import java.util.Date;

import datetime.DateTime;


public class TimeDialog implements OnClickListener, TimePicker.TimePickerListener {
    Dialog timeDialog;
    private Context context;
    private Display display;
    private TimePicker tPicker;

    public TimeDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public TimeDialog build(TimeDialogCallback tdc) {
        this.tdc = tdc;
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_time_dialog, null);
        timeDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        timeDialog.setContentView(view);

        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        tPicker = (TimePicker) view.findViewById(R.id.time_picker);
        tPicker.setTimePickerListener(this);
        ViewGroup.LayoutParams lp1 = tPicker.getLayoutParams();
        lp1.height = WindowUtil.sp2px(context, context.getResources().getDimension(R.dimen.font_size_s_large)) * 11;
        tPicker.setLayoutParams(lp1);

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

    @Override
    public void onPick(DateTime time) {
        selectedDateTime = time;
    }

    public interface TimeDialogCallback {
        public void onSelectDatetime(Date dTime);
    }

    public void show() {
        if (timeDialog != null) {
            timeDialog.setCancelable(true);
            timeDialog.show();
        }
        if (tPicker != null) {
            selectedDateTime = tPicker.getDateTime();
        }
    }

    @Override
    public void onClick(View v) {
        Log.i("info", "点击");
        switch (v.getId()) {
            case R.id.cancle:
                timeDialog.dismiss();
                break;
            case R.id.confirm:
                timeDialog.dismiss();
                if (null != tdc) {
                    tdc.onSelectDatetime(getSelectDateTime().convertToDate());
                }
                break;
        }
    }

    TimeDialogCallback tdc;

    private DateTime selectedDateTime;

    public DateTime getSelectDateTime() {
        return selectedDateTime;
    }
}
