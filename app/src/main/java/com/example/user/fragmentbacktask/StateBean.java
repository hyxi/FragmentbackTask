package com.example.user.fragmentbacktask;

/**
 * Created by huangyuxi on 2019-08-05
 * Title: 状态bean
 */
public class StateBean {
    private String time;
    private String message;

    public StateBean(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
