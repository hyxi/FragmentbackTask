package com.example.user.fragmentbacktask.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangyx on 2016/5/14.
 */
public class MoiveBean implements Parcelable {

    public String imgurl;
    public String title;
    public String content;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgurl);
        dest.writeString(title);
        dest.writeString(content);
    }


    public static final Creator<MoiveBean> CREATOR = new Creator<MoiveBean>() {
        @Override
        public MoiveBean createFromParcel(Parcel source) {
            return new MoiveBean(source);
        }

        @Override
        public MoiveBean[] newArray(int size) {
            return new MoiveBean[size];
        }
    };

    public MoiveBean(Parcel in) {
        imgurl = in.readString();
        title = in.readString();
        content = in.readString();
    }


}
