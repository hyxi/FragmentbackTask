package com.example.user.fragmentbacktask.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by user on 2016/10/26.
 */

public class one implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("parentId")
    public String id ;
    @SerializedName("name")
    public String name  ;
}
