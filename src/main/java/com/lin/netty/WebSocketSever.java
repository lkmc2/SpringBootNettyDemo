package com.lin.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * Netty WebSocket 服务器
 * @author lkmc2
 * @date 2019/9/13 11:11
 */
@Component
public class WebSocketSever {

    /** Netty WebSocket 服务器单例类 **/
    private static class SingletonWebSocketSever {
        private final static WebSocketSever INSTANCE = new WebSocketSever();
    }

    /**
     * 获取 Netty WebSocket 服务器
     * @return Netty WebSocket 服务器
     */
    public static WebSocketSever getInstance() {
        return SingletonWebSocketSever.INSTANCE;
    }

    /** 主线程组 **/
    private NioEventLoopGroup mainGroup;

    /** 从线程组 **/
    private NioEventLoopGroup subGroup;

    /** Netty 服务器 **/
    private ServerBootstrap server;

    /** 服务器通道 **/
    private ChannelFuture future;

    public WebSocketSever() {
        // 访问：http://localhost:9999

        // 定义一对线程组
        // 主线程组，用于接受客户端的连接，但是不做任何处理
        mainGroup = new NioEventLoopGroup();
        // 从线程组，用于处理主线程组传过来的任务
        subGroup = new NioEventLoopGroup();

        // Netty 服务器的创建，ServerBootstrap 是一个启动类
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)  // 设置线程组
                .channel(NioServerSocketChannel.class) // 设置 Nio 的 Socket 双向通道
                .childHandler(new WebSocketServerInitializer()); // 子处理器，用于处理 worker 线程的事件
    }

    /**
     * 启动 Netty WebSocket 服务器
     */
    public void start() {
        // 启动 server ，并设置 9999 为启动的端口号
        this.future = server.bind(9999);
        System.err.println("Netty WebSocket 服务器启动完成...");
    }

}
