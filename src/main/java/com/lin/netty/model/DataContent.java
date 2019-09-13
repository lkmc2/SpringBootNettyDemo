package com.lin.netty.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 浏览器传来的数据内容实体
 * @author lkmc2
 * @date 2019/9/13 17:42
 */
@Data
public class DataContent implements Serializable {

    private static final long serialVersionUID = 447415741521564133L;

    private Integer action; // 动作类型
    private ChatMsg chatMsg; // 聊天消息
    private String extend; // 拓展字段
}
