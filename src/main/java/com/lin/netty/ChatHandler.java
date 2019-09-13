package com.lin.netty;
import	java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lin.enums.MsgActionEnum;
import com.lin.netty.model.ChatMsg;
import com.lin.netty.model.DataContent;
import com.lin.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 聊天处理助手类
 *
 * TextWebSocketFrame：用于为 WebSocket 专门处理文本的对象，frame 是消息的载体
 * @author lkmc2
 * @date 2019/9/13 11:32
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /** 用于记录和管理所有客户端的 Channel **/
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame socketFrame) throws Exception {
        // 获取客户端传输过来的消息
//        String content = socketFrame.text();
//        System.out.println("接收到的数据：" + content);

        // 向所有客户端刷新同一条消息的方式1
        // 遍历每一个客户端的通道
//        for (Channel client : clients) {
//            // 刷新消息到客户端（因为当前类的泛型是 TextWebSocketFrame ，所以返回的对象也需要是这个类型）
//            client.writeAndFlush(new TextWebSocketFrame(
//                            String.format("服务器在 【%s】 接收到消息，消息为：【%s】",
//                            LocalDateTime.now(), content)));
//        }

        // 向所有客户端刷新同一条消息的方式2
//        clients.writeAndFlush(new TextWebSocketFrame(
//                        String.format("服务器在 【%s】 接受到消息，消息为：【%s】",
//                        LocalDateTime.now(), content)));

        // 获取当前通道
        Channel currentChannel = ctx.channel();

        // 1.获取客户端发来的消息
        String content = socketFrame.text();
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();

        // 2.判断消息类型，根据不同的类型来处理不同的业务
        switch (MsgActionEnum.getType(action)) {
            case CONNECT:{
                // 2.1 当 WebSocket 第一次 open 时，初始化 Channel，把用的 Channel 和 userId 关联起来
                String senderId = dataContent.getChatMsg().getSenderId();
                System.out.println("当前发送者的id为：" + senderId);

                // 测试
                for (Channel client : clients) {
                    System.out.println(client.id().asLongText());
                }

                // 打印用户和通道的所有关联信息
                UserChannelRelationship.output();

                // 存储用户和通道的关系
                UserChannelRelationship.put(senderId, currentChannel);
                break;
            }
            case CHAT: {
                // 2.2 聊天类型的信息，把聊天记录保存到数据库，同时标记消息的签收状态【未签收】
                ChatMsg chatMsg = dataContent.getChatMsg();
                String msg = chatMsg.getMsg();
                String receiverId = chatMsg.getReceiverId();
                String senderId = chatMsg.getSenderId();

                // 保存消息到数据库，并且标记为【未签收】
                // 因为不能直接将 Spring 的 bean 对象直接注入当前类，所以需要从持有 Spring 上下文的工具类中获取 bean
//                UserService userService = SpringUtils.getBean("userServiceImpl", UserService.class);
//                String msgId = userService.saveMsg(chatMsg);
//                chatMsg.setMsgId(msgId);
                chatMsg.setMsgId("MSG1001");

                // 发送消息
                // 从全局用户和通道关联关系获取接收方的通道
                Channel receiverChannel = UserChannelRelationship.get(receiverId);

                if (ObjectUtil.isNull(receiverChannel)) {
                    // Channel 为空代表用户离线，推送消息（可使用JPush，小米推送等）
                } else {
                    // 当 receiverChannel 非空时，去 ChannelGroup 去查找对应 Channel 是否存在
                    Channel findChannel = clients.find(receiverChannel.id());

                    if (ObjectUtil.isNotNull(findChannel)) {
                        // 用户在线
                        // 刷新 json 信息到客户端
                        receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatMsg)));
                    } else {
                        // 用户离线，推送消息
                    }
                }
                break;
            }
            case SIGNED: {
                // 2.3 签收消息类型，针对具体的消息进行验收，修改数据库中对应消息的签收状态【已签收】
                // 因为不能直接将 Spring 的 bean 对象直接注入当前类，所以需要从持有 Spring 上下文的工具类中获取 bean
//                UserService userService = SpringUtils.getBean("userServiceImpl", UserService.class);

                // 拓展字段 在 signed 类型的消息中，代表需要去签收的消息 id ，逗号间隔
                String msgIdsStr = dataContent.getExtend();
                String[] msgIds = msgIdsStr.split(",");

                List<String> msgIdList = Lists.newArrayList();


                // 添加非空 id 到列表中（方式1）
                for (String msgId : msgIds) {
                    if (StrUtil.isNotBlank(msgId)) {
                        msgIdList.add(msgId);
                    }
                }

                // 添加非空 id 到列表中（方式2）
//                CollectionUtil.toList(msgIds).stream().filter(StrUtil::isNotBlank).forEach(msgIdList::add);

                // 添加非空 id 到列表中（方式3）
//                List<String> msgIdList = CollectionUtil.toList(msgIds).stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());

                System.out.println(msgIdList);

                if (CollectionUtil.isNotEmpty(msgIdList) && msgIdList.size() > 0) {
                    // 批量签收消息
//                    userService.updateMsgSigned(msgIdList);
                }
                break;
            }
            case KEEPALIVE: {
                // 2.4 心跳类型的消息
                break;
            }
            default :
                throw new RuntimeException("找不到对应的消息类型");
        }
    }

    /**
     * 添加当前 Handler 时执行的方法
     *
     * 当客户端连接到服务器之后（打开连接）
     * 获取客户端的 Channel ，并且放到 ChannelGroup 中去进行管理
     * @param ctx 通道处理上下文
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 将当前下文中的通道添加到 Channel 组中
        clients.add(ctx.channel());
    }

    /**
     * 移除当前 Handler 时执行的方法
     * @param ctx 通道处理上下文
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 将当前下文中的通道从 Channel 组中移除
        // 当触发 handlerRemoved ，ChannelGroup 会自动移除对应的客户端的 Channel，remove 方法不需要显式写出
        clients.remove(ctx.channel());

        Channel channel = ctx.channel();

        System.out.println(String.format("客户端断开，channel 对应的长 id 为：%s", channel.id().asLongText()));
        // 长 id 如：2c337afffe15f96b-00007254-00000001-2f0e727b4a7eb698-ac75d5e7
        System.out.println(String.format("客户端断开，channel 对应的短 id 为：%s", channel.id().asShortText()));
        // 短 id 如：ac75d5e7

        // 短 id 是截取长 id 的最后一段，如果有多个客户端连接时，使用短 id 可能会找到多个不同的客户端
    }

    /**
     * 通道发生异常时调用的方法
     * @param ctx 通道处理上下文
     * @param cause 发生的异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常之后关闭连接（关闭 Channel）
        ctx.channel().close();
        // 将当前的 Channel 从 ChannelGroup 中移除
        clients.remove(ctx.channel());
    }

}
