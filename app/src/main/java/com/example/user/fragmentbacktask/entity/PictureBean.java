package com.example.user.fragmentbacktask.entity;

/**
 * Created by user on 2016/1/27.
 */
public class PictureBean {

    private int image;
    private String picName;

    public PictureBean(int image, String picName) {
        this.image = image;
        this.picName = picName;
    }

    public PictureBean() {
    }

    public int getImage() {
        return image;
    }

    public void setPicUrl(int image) {
        this.image = image;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "picUrl='" + image + '\'' +
                ", picName='" + picName + '\'' +
                '}';
    }
}
