package com.example.user.fragmentbacktask.entity;

import java.io.Serializable;

/**
 * 聊天表情实体类
 */
public class ChatEmoji implements Serializable {
    private static final long serialVersionUID = 1L;

    public String url;//表情图片url
    public String native_url;//表情图片本地url
    public String name;//表情文字
    public String filename;//表情图片名
    public String fileLen;//文件大小
    public String emoji_id;
    public String drawable;
}
