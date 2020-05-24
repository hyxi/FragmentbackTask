package com.example.user.fragmentbacktask.messager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangyuxi on 2019-08-28
 * Title:
 */
public class Book implements Parcelable {

    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book(Parcel source) {
        this.name = source.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Book[size];
        }
    };
}
