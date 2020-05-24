package com.example.user.fragmentbacktask.viewmodel;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
public class DiffCallbackTest extends DiffUtil.Callback {
    private List<String> oldList;
    private List<String> newList;

    public DiffCallbackTest(List<String> oldList, List<String> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return 0;
    }

    @Override
    public int getNewListSize() {
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getClass().equals(newList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
