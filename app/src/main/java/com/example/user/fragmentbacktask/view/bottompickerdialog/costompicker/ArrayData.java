package com.example.user.fragmentbacktask.view.bottompickerdialog.costompicker;

import java.util.ArrayList;

/**
 * 数组数据
 * Created by wood on 2015/12/10.
 */
public class ArrayData extends PickerData {
    @Override
    public int getType() {
        return TYPE_ARRAY;
    }

    ArrayList data;

    public ArrayData(ArrayList data) {
        this.data = data;
    }

    public ArrayList getData() {
        return data;
    }
}
