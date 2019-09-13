package com.lin.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocket 服务器初始化器
 * @author lkmc2
 * @date 2019/9/13 11:16
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // 向管道添加助手工具类（相当于各种拦截器）
        // ============= 添加用于支持 http 的助手类 =============
        // web socket 基于 http ，所以要有 http 编解码器
        pipeline.addLast(new HttpServerCodec());

        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        // 对 http 进行聚合，聚合成 FullHttpRequest 或 FullHttpResponse
        // 几乎在 netty 中的编程，都会使用此 Handler
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));


        // ============= 添加用于支持 WebSocket 的助手类 =============
        // WebSocket 服务器处理的协议，用于指定给客户端连接访问的路由：/ws
        // 本 Handler 会帮你处理一些繁重的复杂的事
        // 会帮你处理握手动作：handshaking（close、ping、pong）ping + pong = 心跳
        // 对于 WebSocket 来讲，都是以 frames 进行传输的，不同的数据类型对应的 frames 也不同
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // ============= 添加自定义的助手类 =============
        // 用于处理聊天消息的助手类
        pipeline.addLast(new ChatHandler());
    }
}
