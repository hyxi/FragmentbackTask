package com.example.user.fragmentbacktask.entity;

/**
 * Created by hyxi
 */

public class TestBean {

    private int id;
    private String content;

    public TestBean() {
    }

    public TestBean(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
