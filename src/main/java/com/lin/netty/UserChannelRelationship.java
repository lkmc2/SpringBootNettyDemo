package com.lin.netty;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户和通道的关联关系的 Map
 * @author lkmc2
 * @date 2019/9/13 18:08
 */
public class UserChannelRelationship {

    /** 存储用户和通道的关联关系的 Map **/
    private static Map<String, Channel> map = new ConcurrentHashMap<>();

    /**
     * 用户和通道的关联关系
     * @param userId 用户id
     * @param channel 通道
     */
    public static void put(String userId, Channel channel) {
        map.put(userId, channel);
    }

    /**
     * 根据用户id获取通道
     * @param userId 用户id
     * @return 通道
     */
    public static Channel get(String userId) {
        return map.get(userId);
    }

    /**
     * 打印用户和通道的所有关联信息
     */
    public static void output() {
        for (Map.Entry<String, Channel> entry : map.entrySet()) {
            System.out.println(String.format("User Id: %s, Channel Id: %s", entry.getKey(), entry.getValue().id().asLongText()));
        }
    }

}
