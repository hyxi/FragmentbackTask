package com.example.user.fragmentbacktask.viewmodel.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by huangyuxi on 2019-08-22
 * Title:
 */
@Entity
public class Pet {
    @PrimaryKey
    public int id;     // Pet id
    public int userId; // User id
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
