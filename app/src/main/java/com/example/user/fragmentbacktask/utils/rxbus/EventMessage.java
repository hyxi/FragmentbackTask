package com.example.user.fragmentbacktask.utils.rxbus;

/**
 * Created by huangyuxi on 2019-05-13
 * Title:
 */
public class EventMessage {
    public String event;
    public Object data;

    public EventMessage() {
    }

    public EventMessage(String event, Object data) {
        this.event = event;
        this.data = data;
    }
}
