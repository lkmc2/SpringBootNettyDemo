package com.lin.enums;

/**
 * 发送消息的动作 枚举
 * @author lkmc2
 */
public enum MsgActionEnum {
	CONNECT(1, "第一次(或重连)初始化连接"),
	CHAT(2, "聊天消息"),	
	SIGNED(3, "消息签收"),
	KEEPALIVE(4, "客户端保持心跳"),
	PULL_FRIEND(5, "拉取好友");
	
	public final Integer type;
	public final String content;
	
	MsgActionEnum(Integer type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Integer getType() {
		return type;
	}

	public static MsgActionEnum getType(Integer type) {
		for (MsgActionEnum msgActionEnum : values()) {
			if (msgActionEnum.getType().equals(type)) {
				return msgActionEnum;
			}
		}
		throw new RuntimeException("找不到对应的枚举类型");
	}

}
