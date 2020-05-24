package com.example.user.fragmentbacktask.view.seekbar;

/**
 * Created by huangyuxi on 2019-06-13
 */
public class ItemRangeData {
    private float averageMonth;
    private String name;
    private float distance;

    public ItemRangeData(String name, float averageMonth) {
        this.averageMonth = averageMonth;
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAverageMonth() {
        return averageMonth;
    }

    public void setAverageMonth(float averageMonth) {
        this.averageMonth = averageMonth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
