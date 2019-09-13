package com.lin.netty.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 浏览器传来的聊天消息实体
 * @author lkmc2
 * @date 2019/9/13 17:42
 */
@Data
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 447415741521564151L;

    private String senderId; // 发送者id
    private String receiverId; // 接收者id
    private String msg; // 聊天内容
    private String msgId; // 用于消息签收的id
}
