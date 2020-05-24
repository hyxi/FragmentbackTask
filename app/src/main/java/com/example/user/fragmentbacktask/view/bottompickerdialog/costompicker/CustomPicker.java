package com.example.user.fragmentbacktask.view.bottompickerdialog.costompicker;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.bottompickerdialog.ScrollableView;
import com.example.user.fragmentbacktask.view.bottompickerdialog.WheelView;
import com.example.user.fragmentbacktask.view.bottompickerdialog.WindowUtil;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.ArrayWheelAdapter;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.NumberWheelAdapter;
import com.example.user.fragmentbacktask.view.bottompickerdialog.adapter.WheelAdapter;

import java.util.List;


/**
 * 通用的滚动选择器
 * 根据数据源设置选择器的内容
 * <p/>
 * 数据源分两种：纯数字和字符串集合
 * 在类初始化完成后手动调用 setData()方法
 * <p/>
 */
public class CustomPicker extends LinearLayout {
    private static final int MSG_CUSTOMTYPE_PICKED = 0;

    private List<PickerData> data;//数据源
    private WheelView[] wheels; //所有的view集合
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CUSTOMTYPE_PICKED:
                    if (mOnPickListener != null) {
                        mOnPickListener.onPickIndexs(getPickedIndexs());
                    }
                    break;
            }
        }
    };

    private int[] getPickedIndexs() {
        int[] result = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = wheels[i].getCurrentItemIndex();
        }
        return result;
    }

    public CustomPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setData(List<PickerData> data) {
        this.data = data;
        init();
    }

    protected void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        int count = data.size();
        if (data == null || count == 0) {
            return;
        }
        wheels = new WheelView[count];
        ScrollableView.ScrollListener listener = createScrollListener();
        for (int i = 0; i < count; i++) {
            PickerData pd = data.get(i);
            wheels[i] = (WheelView) LayoutInflater.from(getContext()).inflate(R.layout.item_wheelview, null);
            wheels[i].setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            int type = pd.getType();
            WheelAdapter adapter = null;
            switch (type) {
                case PickerData.TYPE_ARRAY:
                    adapter = new ArrayWheelAdapter(((ArrayData) pd).getData());
                    break;
                case PickerData.TYPE_NUMBER:
                    adapter = new NumberWheelAdapter(((NumberData) pd).getMin(), ((NumberData) pd).getMax(), ((NumberData) pd).getInterval(), ((NumberData) pd).getUnit());
                    break;
            }
            wheels[i].setAdapter(adapter);
            addView(wheels[i]);
            wheels[i].setScrollListener(listener);
        }
        WindowUtil.resize(this);
    }

    private ScrollableView.ScrollListener createScrollListener() {
        return new ScrollableView.ScrollListener() {

            @Override
            public void onScrollEnd(View v) {
                mHandler.removeMessages(MSG_CUSTOMTYPE_PICKED);
                mHandler.sendEmptyMessageDelayed(MSG_CUSTOMTYPE_PICKED, 200);
            }
        };
    }

    private OnPickListener mOnPickListener;

    public void setmOnPickListener(OnPickListener mOnPickListener) {
        this.mOnPickListener = mOnPickListener;
        mHandler.sendEmptyMessage(MSG_CUSTOMTYPE_PICKED);
    }

    public void setStartStatus(int[] defaultIndexs) {
        for (int i = 0; i < wheels.length; i++) {
            wheels[i].select((i < defaultIndexs.length ? defaultIndexs[i] : 0) < 0 ? 0 : (i < defaultIndexs.length ? defaultIndexs[i] : 0));
        }
    }

    public interface OnPickListener {
        /**
         * @param indexs 所有的下标
         */
        public void onPickIndexs(int... indexs);
    }

}
