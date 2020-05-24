package com.example.user.fragmentbacktask.entity;

import java.util.ArrayList;

/**
 * Created by user on 2016/11/28.
 */

public class ListInfo {

    private ArrayList<String> adlist;
    private ArrayList<MoiveBean> dataList;

    public ArrayList<String> getAdlist() {
        return adlist;
    }

    public void setAdlist(ArrayList<String> adlist) {
        this.adlist = adlist;
    }

    public ArrayList<MoiveBean> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<MoiveBean> dataList) {
        this.dataList = dataList;
    }
}
