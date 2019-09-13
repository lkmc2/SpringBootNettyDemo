package com.lin.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

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
        String content = socketFrame.text();
        System.out.println("接收到的数据：" + content);

        // 向所有客户端刷新同一条消息的方式1
        // 遍历每一个客户端的通道
//        for (Channel client : clients) {
//            // 刷新消息到客户端（因为当前类的泛型是 TextWebSocketFrame ，所以返回的对象也需要是这个类型）
//            client.writeAndFlush(new TextWebSocketFrame(
//                            String.format("服务器在 【%s】 接收到消息，消息为：【%s】",
//                            LocalDateTime.now(), content)));
//        }

        // 向所有客户端刷新同一条消息的方式2
        clients.writeAndFlush(new TextWebSocketFrame(
                        String.format("服务器在 【%s】 接受到消息，消息为：【%s】",
                        LocalDateTime.now(), content)));
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
        // clients.remove(ctx.channel());

        Channel channel = ctx.channel();

        System.out.println(String.format("客户端断开，channel 对应的长 id 为：%s", channel.id().asLongText()));
        // 长 id 如：2c337afffe15f96b-00007254-00000001-2f0e727b4a7eb698-ac75d5e7
        System.out.println(String.format("客户端断开，channel 对应的短 id 为：%s", channel.id().asShortText()));
        // 短 id 如：ac75d5e7

        // 短 id 是截取长 id 的最后一段，如果有多个客户端连接时，使用短 id 可能会找到多个不同的客户端
    }

}
