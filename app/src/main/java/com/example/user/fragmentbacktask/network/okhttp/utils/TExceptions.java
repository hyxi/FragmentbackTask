package com.example.user.fragmentbacktask.network.okhttp.utils;

public class TExceptions {

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }
}
