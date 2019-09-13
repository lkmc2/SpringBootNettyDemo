package com.lin;

import com.lin.netty.WebSocketSever;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Netty 服务器启动器
 * @author lkmc2
 * @date 2019/9/13 16:51
 */
@Component
public class NettyBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Spring Application 刷新时，如果上下文不为空
        if (Objects.isNull(event.getApplicationContext().getParent())) {
            try {
                // 启动 Netty WebSocket 服务器
                WebSocketSever.getInstance().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
