package com.example.user.fragmentbacktask.utils.eventbus;

/**
 * Created by huangyuxi on 2019-07-17
 * Title:
 */
public class MessageEvent {
    private int code;
    private Object data;

    public MessageEvent(int code) {
        this.code = code;
    }

    public MessageEvent(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
