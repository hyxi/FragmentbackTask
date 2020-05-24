package com.example.user.fragmentbacktask.entity;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by huangyx on 2016/9/7.
 */
public class ChatPositionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public String place;
    public String address;
    public LatLng location;
    public boolean isChoose;

}
