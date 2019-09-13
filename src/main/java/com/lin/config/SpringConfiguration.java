package com.lin.config;

import com.lin.utils.SpringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring 配置
 * @author lkmc2
 * @date 2019/9/13 20:46
 */
@Configuration
public class SpringConfiguration {

    /**
     * 将 Spring 上下文注入到 SpringUtils 类中
     * @return Spring 工具类
     */
    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

}
