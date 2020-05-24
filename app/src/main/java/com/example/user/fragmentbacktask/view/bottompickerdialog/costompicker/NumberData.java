package com.example.user.fragmentbacktask.view.bottompickerdialog.costompicker;

/**
 * 纯数字数据
 * Created by wood on 2015/12/10.
 */
public class NumberData extends PickerData {
    @Override
    public int getType() {
        return TYPE_NUMBER;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @param min 最小值
     * @param max 最大值
     * @param interval 相邻值的差
     * @param unit 单位
     */
    public NumberData(int min, int max, int interval, String unit) {
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.unit = unit;
    }

    private int min, max, interval;
    private String unit;//单位

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
