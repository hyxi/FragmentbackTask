package com.example.user.fragmentbacktask.network.okhttp.callback;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一个list
 */

public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = 350954527188831321L;

    private Object bean;
    private ArrayList<T> list;

    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }
    public ArrayList<T>  getList() {
        return list;
    }
    public void setList(ArrayList<T>  list) {
        this.list = list;
    }
}
