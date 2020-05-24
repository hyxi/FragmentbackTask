package com.example.user.fragmentbacktask.viewmodel.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Created by huangyuxi on 2019-08-22
 * Title:
 */
@Entity
public class User {
    @PrimaryKey
    public int id; // User id

    @Ignore
    public List<Pet> pets;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
