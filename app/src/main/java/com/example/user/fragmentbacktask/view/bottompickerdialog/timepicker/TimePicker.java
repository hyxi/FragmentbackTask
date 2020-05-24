package com.example.user.fragmentbacktask.view.bottompickerdialog.timepicker;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.bottompickerdialog.ScrollableView;
import com.example.user.fragmentbacktask.view.bottompickerdialog.WheelView;
import com.example.user.fragmentbacktask.view.bottompickerdialog.WindowUtil;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.ArrayWheelAdapter;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.NumberWheelAdapter;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.WheelAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import datetime.DateTime;

/**
 * 时间选择器
 *
 * @author wangzengyang@gmail.com
 * @since 2013-12-30
 */
public class TimePicker extends RelativeLayout {
    private static final int MSG_TIME_PICKED = 0;
    /**
     * 滚轮：天
     */
    private WheelView mWheelDate;
    /**
     * 滚轮：小时
     */
    private WheelView mWheelHour;
    /**
     * 滚轮：分钟
     */
    private WheelView mWheelMinute;

    /**
     * 滚轮适配器：天
     */
    private WheelAdapter mAdapterDate;
    /**
     * 滚轮适配器：小时
     */
    private NumberWheelAdapter mAdapterHour;
    /**
     * 滚轮适配器：分钟
     */
    private NumberWheelAdapter mAdapterMinute;

    private TimePickerListener mTimePickerListener;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TIME_PICKED:
                    if (mTimePickerListener != null)
                        mTimePickerListener.onPick(getDateTime());
                    break;
            }
        }

        ;
    };

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_time_picker,
                this, true);
        mWheelDate = (WheelView) findViewById(R.id.wheel_view_date);
        mWheelHour = (WheelView) findViewById(R.id.wheel_view_hour);
        mWheelMinute = (WheelView) findViewById(R.id.wheel_view_minute);

        buildAdapters();
        mWheelDate.setAdapter(mAdapterDate);
        mWheelHour.setAdapter(mAdapterHour);
        mWheelMinute.setAdapter(mAdapterMinute);
        ScrollableView.ScrollListener listener = createScrollListener();
        mWheelDate.setScrollListener(listener);
        mWheelHour.setScrollListener(listener);
        mWheelMinute.setScrollListener(listener);
        onSelected(null);
        WindowUtil.resize(this);
    }

    private ScrollableView.ScrollListener createScrollListener() {
        return new ScrollableView.ScrollListener() {

            @Override
            public void onScrollEnd(View v) {
                onSelected(v);
                mHandler.removeMessages(MSG_TIME_PICKED);
                mHandler.sendEmptyMessageDelayed(MSG_TIME_PICKED, 200);
            }
        };
    }

    public void onSelected(View v) {
        if (checkInvalidTime())
            return;
        checkDateAvailable();
        checkHourAvailable();
        checkMinuteAvailable();
    }

    private boolean checkInvalidTime() {
        DateTime selectedDateTime = getDateTime();
        DateTime availableTime = getAvailableDateTime();
        boolean isInvalid = selectedDateTime.isBefore(availableTime);
        if (!isInvalid)
            return isInvalid;
        selectFirstAvailableTime();
        return isInvalid;
    }

    private void selectFirstAvailableTime() {
        selectFirstAvailableDate();
        selectFirstAvailableHour();
        selectFirstAvailableMinute();
    }

    private void selectFirstAvailableMinute() {
        DateTime availableTime = getAvailableDateTime();
        int minute = availableTime.getMinute();
        mWheelMinute.setStartValue(minute);
        mWheelMinute.setCurrentValue(minute);
    }

    private void selectFirstAvailableHour() {
        DateTime availableTime = getAvailableDateTime();
        int hour = availableTime.getHour();
        mWheelHour.setStartValue(hour);
        mWheelHour.setCurrentValue(hour);
    }

    private void selectFirstAvailableDate() {
        if (isTodayAvailable())
            return;
        selectTomorrow();
    }

    private void checkMinuteAvailable() {
        int availableMinuteInSelectedDateHour = getAvailableMinuteInSelectedDateHour();
        DateTime selectedDateTime = getDateTime();
        int selectedMinute = selectedDateTime.getMinute();
        mWheelMinute.setStartValue(availableMinuteInSelectedDateHour);
        if (selectedMinute < availableMinuteInSelectedDateHour)
            mWheelMinute.setCurrentValue(availableMinuteInSelectedDateHour);
        else
            mWheelMinute.setCurrentValue(selectedMinute);
    }

    private void checkHourAvailable() {
        int availableHourInSelectedDate = getAvailableHourInSelectedDate();
        DateTime selectedDateTime = getDateTime();
        int selectedHour = selectedDateTime.getHour();
        mWheelHour.setStartValue(availableHourInSelectedDate);
        if (selectedHour < availableHourInSelectedDate)
            mWheelHour.setCurrentValue(availableHourInSelectedDate);
        else
            mWheelHour.setCurrentValue(selectedHour);
    }

    private boolean isTodayAvailable() {
        DateTime currentTime = getCurrentDateTime();
        DateTime availableTime = getAvailableDateTime();
        return availableTime.getDay() == currentTime.getDay();
    }

    private int getAvailableHourInSelectedDate() {
        DateTime availableTime = getAvailableDateTime();
        if (isTodaySelected())
            return availableTime.getHour();
        if (isTomorrowSelected()) {
            if (isTodayAvailable())
                return 0;
            else
                return availableTime.getHour();
        }
        return 0;
    }

    private int getAvailableMinuteInSelectedDateHour() {
        DateTime availableTime = getAvailableDateTime();
        if (isAvailableDateSelected() && isAvailableHourSelected())
            return availableTime.getMinute();
        return 0;
    }

    private boolean isAvailableDateSelected() {
        DateTime availableTime = getAvailableDateTime();
        DateTime selectedTime = getDateTime();
        return availableTime.getDay() == selectedTime.getDay();
    }

    private boolean isAvailableHourSelected() {
        int selectedHour = mWheelHour.getCurrentValue();
        DateTime availableTime = getAvailableDateTime();
        int availableHour = availableTime.getHour();
        return selectedHour == availableHour;
    }

    private void checkDateAvailable() {
        if (!isTodaySelected())
            return;
        if (!isTodayAvailable())
            selectTomorrow();
    }

    private void selectTomorrow() {
        mWheelDate.select(1);
    }

    private boolean isTodaySelected() {
        return mWheelDate.getCurrentItemIndex() == 0;
    }

    private boolean isTomorrowSelected() {
        return mWheelDate.getCurrentItemIndex() == 1;
    }

    private DateTime getCurrentDateTime() {
        DateTime time = new DateTime();
        return time;
    }

    private DateTime getAvailableDateTime() {
        DateTime availableTime = getCurrentDateTime();
        availableTime.addMinute(15);
        int minute = availableTime.getMinute();
        minute = (minute + 9) / 10 * 10;
        availableTime.setMinute(minute);
        return availableTime;
    }

    private void buildAdapters() {
        List<String> dateData = new ArrayList<String>();// 日期取包括今天的之后60天
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        Calendar gc = GregorianCalendar.getInstance();
        int nw = gc.get(Calendar.DAY_OF_WEEK);
        nw = nw == 1 ? 7 : (nw - 1);// 周几
        String[] h = new String[] { "一", "二", "三", "四", "五", "六", "日" };
        for (int i = 0; i < 60; i++) {
            int dow = gc.get(Calendar.DAY_OF_WEEK);
            dow = dow == 1 ? 7 : (dow - 1);// 周几
            dateData.add("" + sdf.format(gc.getTime()) + "  星期" + h[dow - 1]);
            gc.add(Calendar.DAY_OF_YEAR, 1);
        }
        dateData.set(0, "今天  星期" + h[nw-1]);
        dateData.set(1, "明天  星期" + h[(nw + 1) % 7-1]);
        mAdapterDate = new ArrayWheelAdapter(dateData);
        mAdapterHour = new NumberWheelAdapter(0, 24, 1, "点");
        mAdapterMinute = new NumberWheelAdapter(0, 60, 10, "分");
    }

    /**
     * 获取选择的时间
     *
     * @return
     */
    public DateTime getDateTime() {
        DateTime time = getCurrentDateTime();
        computeDate(time);
        computeHour(time);
        computeMinute(time);
        computeSecond(time);
        return time;
    }

    public String getDayString() {
        return mWheelDate.getCurrentItemString();
    }

    private void computeDate(DateTime time) {
        time.addDay(mWheelDate.getCurrentValue());
    }

    private void computeHour(DateTime time) {
        time.setHour(mWheelHour.getCurrentValue());
    }

    private void computeMinute(DateTime time) {
        time.setMinute(mWheelMinute.getCurrentValue());
    }

    private void computeSecond(DateTime time) {
        time.setSecond(0);
    }

    public void setTimePickerListener(TimePickerListener l) {
        this.mTimePickerListener = l;
    }

    public interface TimePickerListener {
        void onPick(DateTime time);
    }
}
