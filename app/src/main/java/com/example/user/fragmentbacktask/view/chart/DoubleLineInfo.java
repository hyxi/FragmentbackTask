package com.example.user.fragmentbacktask.view.chart;

/**
 * Created by huangyuxi on 2019-06-27
 * Title:
 */
public class DoubleLineInfo {
    private int x;
    private float firstValue;
    private float secondValue;

    public DoubleLineInfo() {
    }

    public DoubleLineInfo(int x, float firstValue, float secondValue) {
        this.x = x;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(float firstValue) {
        this.firstValue = firstValue;
    }

    public float getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(float secondValue) {
        this.secondValue = secondValue;
    }
}
